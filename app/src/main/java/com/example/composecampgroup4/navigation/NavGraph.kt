package com.example.composecampgroup4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.composecampgroup4.presentation.screens.add_jar.addJarRoute
import com.example.composecampgroup4.presentation.screens.details.detailsRoute
import com.example.composecampgroup4.presentation.screens.home.homeRoute

@Composable
fun RootAppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        homeRoute(navController)
        addJarRoute(navController)
        detailsRoute(navController)
    }
}