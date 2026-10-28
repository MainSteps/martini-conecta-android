package com.mainsteps.martiniconecta.navigation

sealed class Routes(val route: String) {
    object Onboarding : Routes("onboarding")
    object Inicio : Routes("inicio")
    object Explorar : Routes("explorar")
    object Favoritos : Routes("favoritos")
    object Perfil : Routes("perfil")
    object Contacto : Routes("contacto")

    object Detalle : Routes("detalle/{itemId}") {

        fun createRoute(itemId: String): String {
            return "detalle/$itemId"
        }
    }
}
