package at.technikum.wien.mse.swe.connector

import kotlin.reflect.KClass

/**
 * Represents a strategy for creating an object at runtime in a specific way.
 */
interface ObjectCreationStrategy {
    /**
     * Creates an object of [type], initialized with the given [data].
     *
     * @param type The [class][KClass] to create an instance of.
     * @param data The data used to initialize the members of the created instance with.
     */
    fun <TClass : Any> create(type: KClass<TClass>, data: List<InstantiationData>): TClass
}