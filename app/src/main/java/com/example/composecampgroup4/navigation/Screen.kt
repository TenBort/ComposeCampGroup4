package com.example.composecampgroup4.navigation

import com.example.composecampgroup4.core.utils.Constants

sealed class Screen(val route: String) {

    data object Home : Screen(ROUTE_HOME)
    data object AddJar : Screen(ROUTE_ADD_JAR)
    data object Details : Screen(ROUTE_DETAILS) {
        fun test() {
            println("sdfs${Constants.APP_ID}")
        }
    }

    companion object {
        private const val ROUTE_HOME = "home_screen"
        private const val ROUTE_ADD_JAR = "add_jar_screen"
        private const val ROUTE_DETAILS = "home_screen"
    }
}