package com.mainsteps.martiniconecta.viewmodel.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PerfilValidatorTest {

    @Test
    fun `telefono vacio es valido`() {
        assertNull(PerfilValidator.errorTelefono(""))
        assertNull(PerfilValidator.errorTelefono("   "))
        assertNull(PerfilValidator.normalizarTelefono(""))
    }

    @Test
    fun `telefono chileno es valido con o sin prefijo y separadores`() {
        val telefonosValidos = listOf(
            "+56994623089",
            "56994623089",
            "994623089",
            "+56 9 9462 3089",
            "+56 (9) 9462-3089",
        )

        telefonosValidos.forEach { telefono ->
            assertNull(PerfilValidator.errorTelefono(telefono))
        }
    }

    @Test
    fun `telefono invalido muestra mensaje del perfil`() {
        val error = PerfilValidator.errorTelefono("94623089")
        assertTrue(error != null)
        assertEquals(
            "Ingresa un telefono chileno de 9 dígitos, o déjalo vacío",
            error,
        )
    }

    @Test
    fun `telefono se normaliza antes de guardar`() {
        val telefonos = listOf(
            "+56994623089",
            "56994623089",
            "994623089",
            "+56 9 9462 3089",
        )

        telefonos.forEach { telefono ->
            assertEquals(
                "+56 9 9462 3089",
                PerfilValidator.normalizarTelefono(telefono),
            )
        }
    }
}
