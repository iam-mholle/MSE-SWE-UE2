package at.technikum.wien.mse.swe.connector

import java.lang.Exception
import java.nio.file.Path
import kotlin.reflect.KClass

inline fun <reified TClass : Any> fixedWidthBlock(path: Path, block: FixedWidthBlockBuilder.() -> Unit): TClass =
        FixedWidthBlockBuilder(path).apply(block).build(TClass::class)

@DslMarker
annotation class FixedWidthBlockDsl

@FixedWidthBlockDsl
class FixedWidthBlockBuilder(private val path: Path) {
    private val simpleColumns: MutableList<SimpleColumnDefinition> = mutableListOf()
    private val complexColumns: MutableList<ComplexColumnDefinition> = mutableListOf()

    infix fun String.block(block: SimpleColumnBuilder.() -> Unit) {
        simpleColumns.add(SimpleColumnBuilder(this).apply(block).build())
    }

    infix fun String.composedOf(block: ComplexColumnBuilder.() -> Unit) {
        complexColumns.add(ComplexColumnBuilder(this).apply(block).build())
    }

    fun <TClass : Any> build(type: KClass<TClass>): TClass {
        return ColumnDefinitionTreeIterator(type, simpleColumns, complexColumns, path).iterate()
    }
}

@FixedWidthBlockDsl
class SimpleColumnBuilder(private val name: String) {
    var index: Int? = null
    var length: Int? = null
    var paddingChar: Char? = null
    var alignment: FieldAlignment? = null

    fun build(): SimpleColumnDefinition {
        return SimpleColumnDefinition(
                name,
                index ?: throw Exception(),
                length ?: throw Exception(),
                paddingChar,
                alignment)
    }
}

@FixedWidthBlockDsl
class ComplexColumnBuilder(private val name: String) {
    private val simpleColumns: MutableList<SimpleColumnDefinition> = mutableListOf()
    private val complexColumns: MutableList<ComplexColumnDefinition> = mutableListOf()

    infix fun String.block(block: SimpleColumnBuilder.() -> Unit) {
        simpleColumns.add(SimpleColumnBuilder(this).apply(block).build())
    }

    infix fun String.composedOf(block: ComplexColumnBuilder.() -> Unit) {
        complexColumns.add(ComplexColumnBuilder(this).apply(block).build())
    }

    fun build(): ComplexColumnDefinition {
        return ComplexColumnDefinition(name, simpleColumns, complexColumns)
    }
}

