package com.example.composecampgroup4.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Immutable
class NavigationState (
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route)
    }

    fun popBackStack() {
        navHostController.popBackStack()
    }

    fun navigateToDetails(jarId: String) {
        navHostController.navigate("${Screen.Details.route}/$jarId")
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController =  rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}