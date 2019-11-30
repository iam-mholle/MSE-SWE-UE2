package at.technikum.wien.mse.swe.connector

import org.junit.Assert.*
import org.junit.Test

class ColumnParserTest {
    @Test
    fun parse_exactString() {
        val columnParser = ColumnParser()
        val input = "testString"
        val startIndex = 0
        val length = 10

        val result = columnParser.parse(input, startIndex, length, ' ', FieldAlignment.RIGHT)

        assertEquals("testString", result)
    }

    @Test
    fun parse_centeredString() {
        val columnParser = ColumnParser()
        val input = "12testString34"
        val startIndex = 2
        val length = 10

        val result = columnParser.parse(input, startIndex, length, ' ', FieldAlignment.RIGHT)

        assertEquals("testString", result)
    }

    @Test
    fun parse_paddedString() {
        val columnParser = ColumnParser()
        val input = "  testString"
        val startIndex = 0
        val length = 12

        val result = columnParser.parse(input, startIndex, length, ' ', FieldAlignment.RIGHT)

        assertEquals("testString", result)
    }

    @Test
    fun parse_paddedStringLeftAligned() {
        val columnParser = ColumnParser()
        val input = "testString  "
        val startIndex = 0
        val length = 12

        val result = columnParser.parse(input, startIndex, length, ' ', FieldAlignment.LEFT)

        assertEquals("testString", result)
    }
}