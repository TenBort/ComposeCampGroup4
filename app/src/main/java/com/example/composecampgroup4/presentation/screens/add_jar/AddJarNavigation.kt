package com.example.composecampgroup4.presentation.screens.add_jar

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.navigation.Screen

fun NavGraphBuilder.addJarRoute(navigationState: NavigationState) {
    composable(route = Screen.AddJar.route) {
        AddJarScreenRoot(navigationState)
    }
}