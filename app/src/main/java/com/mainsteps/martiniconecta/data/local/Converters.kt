package com.mainsteps.martiniconecta.data.local

import androidx.room.TypeConverter
import com.mainsteps.martiniconecta.model.Dificultad
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Ingrediente
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.TamanoNegocio
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class Converters {

    @TypeConverter
    fun divisionToString(division: Division?): String? = division?.name

    @TypeConverter
    fun stringToDivision(value: String?): Division? =
        value?.takeIf { it.isNotBlank() }?.let { enumValueOf<Division>(it) }

    @TypeConverter
    fun tamanoToString(tamano: TamanoNegocio?): String? = tamano?.name

    @TypeConverter
    fun stringToTamano(value: String?): TamanoNegocio? =
        value?.takeIf { it.isNotBlank() }?.let { enumValueOf<TamanoNegocio>(it) }

    @TypeConverter
    fun interesesToString(intereses: Set<Interes>): String =
        intereses.sortedBy { it.name }.joinToString(separator = ",") { it.name }

    @TypeConverter
    fun stringToIntereses(value: String): Set<Interes> =
        if (value.isBlank()) {
            emptySet()
        } else {
            value.split(",").map { it.trim() }.filter { it.isNotEmpty() }.map { enumValueOf<Interes>(it) }.toSet()
        }

    @TypeConverter
    fun dificultadToString(dificultad: Dificultad): String = dificultad.name

    @TypeConverter
    fun stringToDificultad(value: String): Dificultad = enumValueOf(value)

    @TypeConverter
    fun listaToString(valores: List<String>): String =
        valores.joinToString(",") { it.codificar() }

    @TypeConverter
    fun stringToLista(value: String): List<String> =
        value.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.map { it.decodificar() }
            .orEmpty()

    @TypeConverter
    fun ingredientesToString(ingredientes: List<Ingrediente>): String =
        ingredientes.joinToString(",") { ingrediente ->
            listOf(
                ingrediente.nombre.codificar(),
                ingrediente.cantidad.toString(),
                ingrediente.unidad.codificar(),
            ).joinToString("|")
        }

    @TypeConverter
    fun stringToIngredientes(value: String): List<Ingrediente> =
        value.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.map { ingrediente ->
                val partes = ingrediente.split("|")
                Ingrediente(
                    nombre = partes[0].decodificar(),
                    cantidad = partes[1].toDouble(),
                    unidad = partes[2].decodificar(),
                )
            }
            .orEmpty()

    private fun String.codificar(): String =
        URLEncoder.encode(this, StandardCharsets.UTF_8.name())

    private fun String.decodificar(): String =
        URLDecoder.decode(this, StandardCharsets.UTF_8.name())
}
