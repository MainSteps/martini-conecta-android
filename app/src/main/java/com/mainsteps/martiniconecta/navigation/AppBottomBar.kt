package com.mainsteps.martiniconecta.navigation
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

private data class BottomDestination(
    val route: String,
    val label: String,
    val symbol: String
)

private val bottomDestinations = listOf(
    BottomDestination(Routes.Inicio.route, "Inicio", "I"),
    BottomDestination(Routes.Explorar.route, "Explorar", "E"),
    BottomDestination(Routes.Favoritos.route, "Favoritos", "F"),
    BottomDestination(Routes.Perfil.route, "Perfil", "P")
)

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        bottomDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    onNavigate(destination.route)
                },
                icon = {
                    Text(text = destination.symbol)
                },
                label = {
                    Text(text = destination.label)
                }
            )
        }
    }
}