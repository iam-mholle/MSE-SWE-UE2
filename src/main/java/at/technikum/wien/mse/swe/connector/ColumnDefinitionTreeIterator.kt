package at.technikum.wien.mse.swe.connector

import java.lang.Exception
import java.lang.UnsupportedOperationException
import java.nio.file.Path
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure

/**
 * Iterates over a tree of [ComplexColumnDefinitions][ComplexColumnDefinition] and [SimpleColumnDefinitions][SimpleColumnDefinition]
 * and instantiates the object tree of the same shape.
 *
 * @param type The root type in the object tree.
 * @param simpleColumns The [simple columns][simpleColumns] of the root type.
 * @param complexColumns The [complex columns][complexColumns] of the root type.
 * @param path The path to the file containing the string used to initialize the simple columns.
 */
class ColumnDefinitionTreeIterator(
        private val type: KClass<*>,
        private val simpleColumns: List<SimpleColumnDefinition>,
        private val complexColumns: List<ComplexColumnDefinition>,
        path: Path) {
    private val input: String by lazy { path.toFile().readText() }

    /**
     * Iterates the tree.
     *
     * @return An initialized instance of [TClass].
     */
    fun <TClass : Any> iterate(): TClass {
        @Suppress("UNCHECKED_CAST")
        return rec(type, simpleColumns, complexColumns) as TClass
    }

    private fun rec(type: KClass<*>,
                    simpleColumns: List<SimpleColumnDefinition>,
                    complexColumns: List<ComplexColumnDefinition>): Any {
        val simpleColumnInstantiationData = simpleColumns.map { column ->
            val member = type.members.firstOrNull { member -> member.name == column.name }
                    ?: throw createMissingMemberException(column.name, type.qualifiedName ?: "")
            val value = ColumnParser().parse(input, column.index, column.length, column.paddingChar, column.alignment)
            InstantiationData(member as KProperty<*>, value)
        }

        val complexColumnInstantiationData = complexColumns.map { column ->
            val member = type.members.firstOrNull { member -> member.name == column.name }
                    ?: throw createMissingMemberException(column.name, type.qualifiedName ?: "")
            val instance = rec(member.returnType.jvmErasure, column.simpleColumns, column.complexColumns)
            InstantiationData(member as KProperty<*>, instance)
        }

        val instantiationData = simpleColumnInstantiationData + complexColumnInstantiationData
        return ObjectFactory().create(type, instantiationData)
    }

    private fun createMissingMemberException(memberName: String, typeName: String): Exception =
            UnsupportedOperationException("Could not find member '${memberName}' on type '${typeName}'.")
}