package com.mainsteps.martiniconecta.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity

private const val FONT_SCALE_DOS_FILAS = 1.5f

private data class BottomDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomDestinations = listOf(
    BottomDestination(Routes.Inicio.route, "Inicio", Icons.Default.Home),
    BottomDestination(Routes.Explorar.route, "Explorar", Icons.Default.Search),
    BottomDestination(Routes.Favoritos.route, "Favoritos", Icons.Default.Favorite),
    BottomDestination(Routes.Perfil.route, "Perfil", Icons.Default.Person)
)

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    if (LocalDensity.current.fontScale >= FONT_SCALE_DOS_FILAS) {
        Surface(color = NavigationBarDefaults.containerColor) {
            Column(modifier = Modifier.fillMaxWidth()) {
                bottomDestinations.chunked(2).forEach { destinations ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        destinations.forEach { destination ->
                            BottomDestinationItem(
                                destination = destination,
                                currentRoute = currentRoute,
                                onNavigate = onNavigate,
                            )
                        }
                    }
                }
            }
        }
    } else {
        NavigationBar {
            bottomDestinations.forEach { destination ->
                BottomDestinationItem(
                    destination = destination,
                    currentRoute = currentRoute,
                    onNavigate = onNavigate,
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomDestinationItem(
    destination: BottomDestination,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
) {
    NavigationBarItem(
        selected = currentRoute == destination.route,
        onClick = { onNavigate(destination.route) },
        icon = {
            Icon(
                imageVector = destination.icon,
                contentDescription = null,
            )
        },
        label = {
            Text(text = destination.label)
        },
    )
}
