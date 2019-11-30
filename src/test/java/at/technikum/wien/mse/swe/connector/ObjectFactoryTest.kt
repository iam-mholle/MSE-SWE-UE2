package at.technikum.wien.mse.swe.connector

import at.technikum.wien.mse.swe.connector.annotated.AnnotatedJavaDto
import at.technikum.wien.mse.swe.connector.annotated.AnnotatedKotlinDataDto
import at.technikum.wien.mse.swe.connector.annotated.AnnotatedKotlinDto
import org.junit.Assert.*
import org.junit.Test
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class ObjectFactoryTest {
    @Test
    fun create_java() {
        val objectFactory = ObjectFactory()
        val property = AnnotatedJavaDto::class.memberProperties.find { p -> p.name == "name" } as KProperty<*>

        val instance = objectFactory.create(AnnotatedJavaDto::class, listOf(InstantiationData(property, "test")))

        assertEquals("test", instance.name)
    }

    @Test
    fun create_kotlin() {
        val objectFactory = ObjectFactory()
        val property = AnnotatedKotlinDto::class.memberProperties.find { p -> p.name == "name" } as KProperty<*>

        val instance = objectFactory.create(AnnotatedKotlinDto::class, listOf(InstantiationData(property, "test")))

        assertEquals("test", instance.name)
    }

    @Test
    fun create_kotlinData() {
        val objectFactory = ObjectFactory()
        val property = AnnotatedKotlinDataDto::class.memberProperties.find { p -> p.name == "name" } as KProperty<*>

        val instance = objectFactory.create(AnnotatedKotlinDataDto::class, listOf(InstantiationData(property, "test")))

        assertEquals("test", instance.name)
    }
}