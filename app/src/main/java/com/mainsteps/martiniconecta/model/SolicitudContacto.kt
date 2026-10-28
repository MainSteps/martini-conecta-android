package com.mainsteps.martiniconecta.model

enum class ProductoInteres(val etiqueta: String) {
    MANTECA_UNIGRAL("Manteca Unigral"),
    MARGARINA_GRAN_MASTER_HOJA("Margarina Gran Master Hoja"),
    BRAVO_CREM("Bravo Crem"),
    MONNALISA("Monnalisa"),
    CHOCOLATE_CARAVELLA("Chocolate Caravella"),
    CHOCOLATE_ARIBA("Chocolate Ariba"),
    LINEA_VEGANA_ORASI("Línea vegana Orasì"),
}

data class SolicitudContacto(
    val id: Long = 0,
    val nombre: String,
    val telefono: String,
    val division: Division,
    val comuna: String,
    val producto: ProductoInteres,
    val creadaEn: Long,
)
