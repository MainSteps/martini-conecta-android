package com.mainsteps.martiniconecta.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InicioScreen(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Inicio",
        description = "Aquí aparecerá la actividad principal de Martini Conecta.",
        modifier = modifier
    )
}

@Composable
fun FavoritosScreen(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Favoritos",
        description = "Aquí encontrarás el contenido que hayas guardado.",
        modifier = modifier
    )
}

@Composable
fun PerfilScreen(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Perfil",
        description = "Aquí podrás consultar y editar tu información personal.",
        modifier = modifier
    )
}