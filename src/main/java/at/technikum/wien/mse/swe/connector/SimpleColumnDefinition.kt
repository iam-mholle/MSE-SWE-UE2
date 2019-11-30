package at.technikum.wien.mse.swe.connector

/**
 * Defines a simple property on a type.
 *
 * @property name The name of the property of the type.
 * @property index The starting index of the fixed width column.
 * @property length The length of the fixed width column.
 * @property paddingChar The padding character of the fixed width column.
 * @property alignment The alignment of the value in the fixed width column.
 */
data class SimpleColumnDefinition(val name: String, val index: Int, val length: Int, val paddingChar: Char?, val alignment: FieldAlignment?)
