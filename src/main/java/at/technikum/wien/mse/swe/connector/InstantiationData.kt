package at.technikum.wien.mse.swe.connector

import kotlin.reflect.KProperty

/**
 * Contains the data needed for DTO instantiation.
 *
 * @property property The [property][KProperty] to initialize.
 * @property value The value to initialize the given [property] with.
 */
data class InstantiationData(val property: KProperty<*>, val value: Any)
