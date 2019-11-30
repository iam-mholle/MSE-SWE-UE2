package at.technikum.wien.mse.swe.connector

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.jvm.isAccessible

/**
 * Used to create an instance of a class defining an empty(/implicit default) constructor.
 */
class EmptyConstructorObjectCreationStrategy : ObjectCreationStrategy {
    /**
     * @inheritDoc
     */
    override fun <TClass : Any> create(type: KClass<TClass>, data: List<InstantiationData>): TClass {
        val instance = type.createInstance()

        data.forEach { d ->
            val mutableProperty = getMutableProperty(d.property)
            mutableProperty.setter.call(instance, d.value)
        }

        return instance
    }

    private fun getMutableProperty(property: KProperty<*>): KMutableProperty<*> {
        property.isAccessible = true
        return property as KMutableProperty<*>
    }
}