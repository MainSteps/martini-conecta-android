package com.mainsteps.martiniconecta.viewmodel.validation

import com.mainsteps.martiniconecta.model.CampoSeleccion
import com.mainsteps.martiniconecta.model.CampoTexto
import com.mainsteps.martiniconecta.model.ContactoUiState
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactoValidatorTest {

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
            assertNull(ContactoValidator.errorTelefono(telefono))
        }
    }

    @Test
    fun `telefono de ocho digitos es invalido`() {
        assertTrue(ContactoValidator.errorTelefono("94623089") != null)
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
                ContactoValidator.normalizarTelefono(telefono),
            )
        }
    }

    @Test
    fun `formulario completo es valido`() {
        val state = ContactoUiState(
            nombre = CampoTexto("Ana Pérez"),
            telefono = CampoTexto("+56 9 1234 5678"),
            division = CampoSeleccion(Division.GELATERIA),
            comuna = CampoTexto("Maipú"),
            producto = CampoSeleccion(ProductoInteres.CHOCOLATE_ARIBA),
        )

        assertTrue(ContactoValidator.esFormularioValido(state))
    }

    @Test
    fun `formulario vacio es invalido`() {
        assertFalse(ContactoValidator.esFormularioValido(ContactoUiState()))
    }
}
