package com.mainsteps.martiniconecta.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MartiniColorScheme = lightColorScheme(
    primary = MartiniBlueAccessible,
    onPrimary = OnMartiniBlue,
    primaryContainer = MartiniBlueContainer,
    onPrimaryContainer = OnMartiniBlueContainer,
    secondary = MartiniTextSecondary,
    onSecondary = MartiniSurface,
    secondaryContainer = MartiniSurfaceVariant,
    onSecondaryContainer = MartiniText,
    background = MartiniBackground,
    onBackground = MartiniText,
    surface = MartiniSurface,
    onSurface = MartiniText,
    surfaceVariant = MartiniSurfaceVariant,
    onSurfaceVariant = MartiniTextSecondary,
    outline = MartiniOutline,
    error = MartiniError,
    onError = OnMartiniError,
    errorContainer = MartiniErrorContainer,
    onErrorContainer = OnMartiniErrorContainer,
    surfaceTint = MartiniBlue
)

@Composable
fun MartiniConecta_Grupo15Theme(
    letraGrande: Boolean = false,
    content: @Composable () -> Unit,
) {
    val typography = if (letraGrande) {
        Typography.escalada(LETRA_GRANDE_FACTOR)
    } else {
        Typography
    }
    MaterialTheme(
        colorScheme = MartiniColorScheme,
        typography = typography,
        content = content
    )
}
