package com.mainsteps.martiniconecta.data.local

import androidx.room.TypeConverter
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Interes
import com.mainsteps.martiniconecta.model.TamanoNegocio

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
}
