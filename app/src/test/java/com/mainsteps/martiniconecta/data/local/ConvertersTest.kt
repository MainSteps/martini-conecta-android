package com.mainsteps.martiniconecta.data.local

import com.mainsteps.martiniconecta.model.Ingrediente
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `lista conserva textos con separadores y tildes`() {
        val original = listOf("Chocolate, cacao", "Crema 35%", "Marrón | intenso")

        val recuperada = converters.stringToLista(converters.listaToString(original))

        assertEquals(original, recuperada)
    }

    @Test
    fun `ingredientes conservan todos sus datos`() {
        val original = listOf(
            Ingrediente("Chocolate, cacao", 1.5, "kg | unidad"),
            Ingrediente("Crema", 250.0, "ml"),
        )

        val recuperados = converters.stringToIngredientes(
            converters.ingredientesToString(original)
        )

        assertEquals(original, recuperados)
    }
}
