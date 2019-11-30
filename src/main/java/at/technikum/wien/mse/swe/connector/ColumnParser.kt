package at.technikum.wien.mse.swe.connector

import java.lang.Exception

/**
 * Allows easy parsing of fixed width blocks.
 */
class ColumnParser {
    /**
     * Extracts the specified fixed width string from a given [input string][input].
     *
     * @param input The [String] to extract the fixed width block from.
     * @param startIndex The start index of the fixed width block.
     * @param length The length of the fixed width block.
     * @param paddingChar The [character][Char] used to pad the field to the specified [length].
     * @param alignment The [alignment][FieldAlignment] of the content in the fixed width block.
     * @return The fixed width block as [string][String].
     */
    fun parse(input: String, startIndex: Int, length: Int, paddingChar: Char?, alignment: FieldAlignment?): String {
        val subString = input.substring(startIndex, startIndex + length)

        return when (alignment) {
            FieldAlignment.LEFT -> subString.trimEnd(paddingChar ?: throw Exception())
            FieldAlignment.RIGHT -> subString.trimStart(paddingChar ?: throw Exception())
            null -> subString
        }
    }
}