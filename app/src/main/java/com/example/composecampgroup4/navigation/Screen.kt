package com.example.composecampgroup4.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    data object Home : Screen(ROUTE_HOME)
    data object AddJar : Screen(ROUTE_ADD_JAR)
    data object Details : Screen(ROUTE_DETAILS) {
        const val JAR_ID_ARG = "jar_id"
        val routeWithArgs = "${route}/{${JAR_ID_ARG}}"
        val arguments = listOf(
            navArgument(JAR_ID_ARG) { type = NavType.StringType }
        )
    }

    companion object {
        private const val ROUTE_HOME = "home_screen"
        private const val ROUTE_ADD_JAR = "add_jar_screen"
        private const val ROUTE_DETAILS = "details_screen"
    }
}