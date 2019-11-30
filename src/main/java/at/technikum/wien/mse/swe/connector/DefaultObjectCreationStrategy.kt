package at.technikum.wien.mse.swe.connector

import java.lang.UnsupportedOperationException
import java.math.BigDecimal
import java.math.MathContext
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

/**
 * Used to create an instance of a class using the primary constructor or single secondary constructor in Kotlin, or a
 * single constructor in Java.
 */
class DefaultObjectCreationStrategy : ObjectCreationStrategy {
    /**
     * @inheritDoc
     * @throws UnsupportedOperationException No fitting constructor for the instantiation of [type] could be found.
     */
    override fun <TClass : Any> create(type: KClass<TClass>, data: List<InstantiationData>): TClass {
        val constructor = getFittingConstructor(type)
        val parameters = constructor.parameters

        val map = parameters
                .map { ctorParam ->
                    ctorParam to convertToParameterType(
                            ctorParam,
                            data.single { data ->
                                isParameterMatch(ctorParam, data.property)
                            }.value)
                }
                .toMap()

        return constructor.callBy(map)
    }

    private fun <TClass : Any> getFittingConstructor(type: KClass<TClass>): KFunction<TClass> = when {
        type.primaryConstructor != null -> type.primaryConstructor!!
        type.constructors.size == 1 -> type.constructors.single()
        else -> throw UnsupportedOperationException("Could not choose a constructor for the instantiation of ${type.qualifiedName}. " +
                "Make sure to either define a primary constructor, or define a single constructor.")
    }

    private fun convertToParameterType(parameter: KParameter, data: Any): Any = when (parameter.type.javaType) {
        BigDecimal::class.java -> BigDecimal(data as String)
        else -> data
    }

    private fun isParameterMatch(parameter: KParameter, property: KProperty<*>): Boolean =
            property.name == parameter.name || property.returnType.javaType == parameter.type.javaType
}
