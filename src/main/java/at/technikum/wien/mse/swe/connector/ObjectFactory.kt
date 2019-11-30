package at.technikum.wien.mse.swe.connector

import java.lang.UnsupportedOperationException
import kotlin.reflect.*
import kotlin.reflect.full.isSubclassOf

/**
 * Provides methods for creating objects at runtime.
 */
class ObjectFactory {
    /**
     * Creates an instance of the specified [class][KClass] using the given [data] to initialize the
     * [properties][KProperty]/fields of the [class][KClass].
     *
     * @param TClass The [class][KClass] to instantiate.
     * @param data The data used to initialize the created instance of [TClass].
     * @return An initialized instance of [TClass].
     *
     * @throws UnsupportedOperationException No fitting constructor for the instantiation of [type] could be found.
     */
    fun <TClass : Any> create(type: KClass<TClass>, data: List<InstantiationData>): TClass {
        return when {
            hasEmptyConstructor(type) -> emptyConstructorObjectCreationStrategy.create(type, data)
            isEnum(type) -> enumObjectCreationStrategy.create(type, data)
            else -> defaultObjectCreationStrategy.create(type, data)
        }
    }

    private fun hasEmptyConstructor(type: KClass<*>) = type.constructors.any { ctor -> ctor.parameters.isEmpty() }

    private fun isEnum(type: KClass<*>) = type.isSubclassOf(Enum::class)

    private companion object {
        val emptyConstructorObjectCreationStrategy = EmptyConstructorObjectCreationStrategy()
        val enumObjectCreationStrategy = EnumObjectCreationStrategy()
        val defaultObjectCreationStrategy = DefaultObjectCreationStrategy()
    }
}
