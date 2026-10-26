package com.mainsteps.martiniconecta.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mainsteps.martiniconecta.ui.screens.DetailScreen
import com.mainsteps.martiniconecta.ui.components.DemoBanner
import com.mainsteps.martiniconecta.ui.screens.ExploreScreen
import com.mainsteps.martiniconecta.ui.screens.FavoritosScreen
import com.mainsteps.martiniconecta.ui.screens.InicioScreen
import com.mainsteps.martiniconecta.ui.screens.OnboardingScreen
import com.mainsteps.martiniconecta.ui.screens.PerfilScreen

private const val PERFIL_GUARDADO_CONFIRMADO = "perfilGuardadoConfirmado"

@Composable
fun AppNavigation(
    hasProfile: Boolean = false
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val mainRoutes = setOf(
        Routes.Inicio.route,
        Routes.Explorar.route,
        Routes.Favoritos.route,
        Routes.Perfil.route
    )

    val startDestination = if (hasProfile) {
        Routes.Inicio.route
    } else {
        Routes.Onboarding.route
    }

    Scaffold(
        topBar = {
            DemoBanner()
        },
        bottomBar = {
            if (currentRoute in mainRoutes) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.Inicio.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Onboarding.route) {
                OnboardingScreen(
                    onPerfilGuardado = {
                        navController.navigate(Routes.Inicio.route) {
                            popUpTo(Routes.Onboarding.route) {
                                inclusive = true
                            }
                        }
                        navController.getBackStackEntry(Routes.Inicio.route)
                            .savedStateHandle[PERFIL_GUARDADO_CONFIRMADO] = true
                    }
                )
            }

            composable(Routes.Inicio.route) { backStackEntry ->
                val perfilGuardadoConfirmado by backStackEntry.savedStateHandle
                    .getStateFlow(PERFIL_GUARDADO_CONFIRMADO, false)
                    .collectAsStateWithLifecycle()

                InicioScreen(
                    perfilGuardadoConfirmado = perfilGuardadoConfirmado,
                    onConfirmacionPerfilLeida = {
                        backStackEntry.savedStateHandle[PERFIL_GUARDADO_CONFIRMADO] = false
                    }
                )
            }

            composable(Routes.Explorar.route) {
                ExploreScreen(
                    onOpenDetail = { itemId ->
                        navController.navigate(
                            Routes.Detalle.createRoute(itemId)
                        )
                    }
                )
            }

            composable(Routes.Favoritos.route) {
                FavoritosScreen()
            }

            composable(Routes.Perfil.route) {
                PerfilScreen()
            }

            composable(
                route = Routes.Detalle.route,
                arguments = listOf(
                    navArgument("itemId") {
                        type = NavType.StringType
                    }
                )
            ) { navBackStackEntry ->
                val itemId = navBackStackEntry.arguments
                    ?.getString("itemId")
                    .orEmpty()

                DetailScreen(
                    itemId = itemId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
