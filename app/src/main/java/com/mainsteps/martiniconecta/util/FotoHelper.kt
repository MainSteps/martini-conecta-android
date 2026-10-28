package com.mainsteps.martiniconecta.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object FotoHelper {
    private const val DIRECTORIO_FOTOS = "profile_photos"

    fun crearUri(context: Context): Uri {
        val directorio = File(context.filesDir, DIRECTORIO_FOTOS).apply { mkdirs() }
        val archivo = File(directorio, "perfil_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            autoridad(context.packageName),
            archivo,
        )
    }

    fun autoridad(packageName: String): String = "$packageName.fileprovider"
}
