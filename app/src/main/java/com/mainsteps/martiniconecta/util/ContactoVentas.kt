package com.mainsteps.martiniconecta.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.mainsteps.martiniconecta.model.Contenido

const val NUMERO_WHATSAPP_VENTAS = "56944570770"

private const val SufijoCompartirReceta = "Receta de Martini Conecta · mastermartini.cl"

fun intentCompartirReceta(contenido: Contenido): Intent {
    val texto = buildString {
        appendLine(contenido.titulo)
        appendLine()
        appendLine(contenido.descripcion)
        appendLine()
        append(SufijoCompartirReceta)
    }
    return Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, texto)
        putExtra(Intent.EXTRA_SUBJECT, contenido.titulo)
    }
}

fun intentConsultarVentas(contenido: Contenido): Intent {
    val mensaje = mensajeWhatsAppVentas(contenido)
    val uri = Uri.parse(
        "https://wa.me/$NUMERO_WHATSAPP_VENTAS?text=${Uri.encode(mensaje)}",
    )
    return Intent(Intent.ACTION_VIEW, uri)
}

fun compartirReceta(context: Context, contenido: Contenido) {
    val intent = intentCompartirReceta(contenido)
    try {
        context.startActivity(
            Intent.createChooser(intent, "Compartir receta"),
        )
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "No hay ninguna app para compartir",
            Toast.LENGTH_SHORT,
        ).show()
    }
}

fun consultarVentas(context: Context, contenido: Contenido) {
    try {
        context.startActivity(intentConsultarVentas(contenido))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "No se pudo abrir WhatsApp",
            Toast.LENGTH_SHORT,
        ).show()
    }
}

private fun mensajeWhatsAppVentas(contenido: Contenido): String {
    val productos = contenido.productosMartini.filter { it.isNotBlank() }
    return if (productos.isEmpty()) {
        "Hola, vi la receta ${contenido.titulo} en Martini Conecta."
    } else {
        val listaProductos = productos.joinToString(", ")
        "Hola, vi la receta ${contenido.titulo} en Martini Conecta y quiero consultar por $listaProductos."
    }
}
