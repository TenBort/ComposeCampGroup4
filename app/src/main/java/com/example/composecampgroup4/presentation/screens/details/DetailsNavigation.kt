package com.example.composecampgroup4.presentation.screens.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.navigation.Screen

fun NavGraphBuilder.detailsRoute(navigationState: NavigationState) {
    composable(
        route = Screen.Details.routeWithArgs,
        arguments = Screen.Details.arguments
    ) { navBackStackEntry ->
        val jarId = navBackStackEntry.arguments?.getInt(Screen.Details.JAR_ID_ARG) ?: -1
        DetailsScreenRoot(navigationState, jarId)
    }
}