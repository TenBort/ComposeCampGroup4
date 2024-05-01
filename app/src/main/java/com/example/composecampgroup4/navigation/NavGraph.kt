package com.example.composecampgroup4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.composecampgroup4.presentation.screens.add_jar.addJarRoute
import com.example.composecampgroup4.presentation.screens.details.detailsRoute
import com.example.composecampgroup4.presentation.screens.home.homeRoute

@Composable
fun RootAppNavigation(
    navigationState: NavigationState,
) {
    NavHost(
        navController = navigationState.navHostController,
        startDestination = Screen.Home.route
    ) {
        homeRoute(navigationState)
        addJarRoute(navigationState)
        detailsRoute(navigationState)
    }
}