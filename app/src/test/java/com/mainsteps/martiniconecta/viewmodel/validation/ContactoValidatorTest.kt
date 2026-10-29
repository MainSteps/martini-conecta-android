package com.mainsteps.martiniconecta.viewmodel.validation

import com.mainsteps.martiniconecta.model.CampoSeleccion
import com.mainsteps.martiniconecta.model.CampoTexto
import com.mainsteps.martiniconecta.model.ContactoUiState
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.ProductoInteres
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactoValidatorTest {

    @Test
    fun `telefono chileno con ocho digitos es valido`() {
        assertNull(ContactoValidator.errorTelefono("+56 9 1234 5678"))
    }

    @Test
    fun `telefono incompleto es invalido`() {
        assertTrue(ContactoValidator.errorTelefono("+56 9 1234") != null)
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
