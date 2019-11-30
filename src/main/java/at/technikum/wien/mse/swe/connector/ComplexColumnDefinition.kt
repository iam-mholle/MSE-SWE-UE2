package at.technikum.wien.mse.swe.connector

/**
 * Represents an aggregation of multiple [simple columns][simpleColumns].
 *
 * @property name The name of the property of the type.
 * @property simpleColumns The simple properties of the type.
 * @property complexColumns The properties of the type containing other properties.
 */
data class ComplexColumnDefinition(
        val name: String,
        val simpleColumns: MutableList<SimpleColumnDefinition>,
        val complexColumns: MutableList<ComplexColumnDefinition>
)