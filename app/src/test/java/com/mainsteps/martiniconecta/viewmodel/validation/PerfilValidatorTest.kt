package com.mainsteps.martiniconecta.viewmodel.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import com.mainsteps.martiniconecta.model.CampoMultiSeleccion
import com.mainsteps.martiniconecta.model.CampoSeleccion
import com.mainsteps.martiniconecta.model.CampoTexto
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.PerfilUiState
import com.mainsteps.martiniconecta.model.TamanoNegocio

class PerfilValidatorTest {

    @Test
    fun `valida correo y nombre sin depender de Android`() {
        assertNull(PerfilValidator.errorCorreo("ana.perez+demo@ejemplo.cl"))
        assertNull(PerfilValidator.errorCorreo(" ANA@EJEMPLO.CL "))
        assertEquals("Correo no válido", PerfilValidator.errorCorreo("ana@localhost"))
        assertEquals("Correo no válido", PerfilValidator.errorCorreo(" "))
        assertNull(PerfilValidator.errorNombre("Ana"))
        assertEquals("Escribe al menos 2 letras", PerfilValidator.errorNombre("A"))
    }

    @Test
    fun `valida campos de seleccion y estado completo del perfil`() {
        assertEquals("Elige una división", PerfilValidator.errorDivision(null))
        assertNull(PerfilValidator.errorDivision(Division.GELATERIA))
        assertEquals("Elige el tamaño de tu negocio", PerfilValidator.errorTamano(null))
        assertNull(PerfilValidator.errorTamano(TamanoNegocio.SOLO_YO))
        assertEquals("Elige al menos un interés", PerfilValidator.errorIntereses(emptySet()))
        assertNull(PerfilValidator.errorIntereses(setOf(Interes.RECETAS)))

        val perfilValido = PerfilUiState(
            nombre = CampoTexto("Ana Pérez"),
            correo = CampoTexto("ana@ejemplo.cl"),
            telefono = CampoTexto("+56 9 1234 5678"),
            division = CampoSeleccion(Division.GELATERIA),
            tamano = CampoSeleccion(TamanoNegocio.SOLO_YO),
            intereses = CampoMultiSeleccion(setOf(Interes.RECETAS)),
        )
        assertTrue(PerfilValidator.esFormularioValido(perfilValido))
        assertFalse(PerfilValidator.esFormularioValido(perfilValido.copy(correo = CampoTexto("mal"))))
    }

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
