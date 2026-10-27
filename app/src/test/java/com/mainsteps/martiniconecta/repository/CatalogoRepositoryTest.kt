package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.model.Division
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogoRepositoryTest {

    private val contenidos = CatalogoRepository.obtenerTodos()

    @Test
    fun catalogoTieneEntreNueveYDoceRecetas() {
        assertTrue(contenidos.size in 9..12)
    }

    @Test
    fun cadaDivisionTieneAlMenosDosRecetas() {
        Division.entries.forEach { division ->
            val cantidad = contenidos.count { it.division == division }
            assertTrue("La división ${division.etiqueta} tiene menos de dos recetas", cantidad >= 2)
        }
    }

    @Test
    fun identificadoresNoSeRepiten() {
        val identificadores = contenidos.map { it.id }
        assertEquals(identificadores.size, identificadores.distinct().size)
    }

    @Test
    fun recetasTienenContenidoCompleto() {
        contenidos.forEach { contenido ->
            assertTrue(contenido.id.isNotBlank())
            assertTrue(contenido.titulo.isNotBlank())
            assertTrue(contenido.descripcion.isNotBlank())
            assertTrue(contenido.categoria.isNotBlank())
            assertTrue(contenido.productosMartini.isNotEmpty())
            assertTrue(contenido.rindePorciones > 0)
            assertTrue(contenido.ingredientes.isNotEmpty())
            assertTrue(contenido.pasos.isNotEmpty())
            assertTrue(contenido.imagenResId != 0)
            assertTrue("La receta contiene texto de relleno", !contenido.descripcion.contains("lorem", true))
        }
    }

    @Test
    fun catalogoIncluyeProductosMartiniSolicitados() {
        val productos = contenidos.flatMap { it.productosMartini }.toSet()
        val requeridos = setOf(
            "Manteca Unigral",
            "Margarina Gran Master Hoja",
            "Bravo Crem",
            "Monnalisa",
            "Chocolate Caravella",
            "Chocolate Ariba",
            "Orasì"
        )

        assertTrue(productos.containsAll(requeridos))
    }

    @Test
    fun obtenerPorIdDevuelveLaRecetaCorrecta() {
        val contenido = CatalogoRepository.obtenerPorId("gelato-chocolate-intenso")

        assertNotNull(contenido)
        assertEquals("Gelato de chocolate intenso", contenido?.titulo)
    }

    @Test
    fun obtenerPorIdDesconocidoDevuelveNull() {
        assertNull(CatalogoRepository.obtenerPorId("receta-inexistente"))
    }
}
