package at.technikum.wien.mse.swe.connector

import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaType
import kotlin.streams.toList

/**
 * Used to initialize an enum at runtime.
 */
class EnumObjectCreationStrategy : ObjectCreationStrategy {
    /**
     * @inheritDoc
     */
    override fun <TClass : Any> create(type: KClass<TClass>, data: List<InstantiationData>): TClass {
        val methods = type.declaredFunctions
        val initMethod = methods.stream()
                .filter { m -> m.name != "valueOf" }
                .filter { m -> m.parameters.size == 1 && m.parameters.first().type.javaType == String::class.java }
                .toList()
                .first()

        val enum = initMethod.call(data.first().value)
        @Suppress("UNCHECKED_CAST")
        return when (enum) {
            is Optional<*> -> enum.get() as TClass
            else -> enum as TClass
        }
    }
}