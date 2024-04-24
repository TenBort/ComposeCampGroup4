package com.example.composecampgroup4.presentation.screens.add_jar

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composecampgroup4.navigation.Screen

fun NavGraphBuilder.addJarRoute(navController: NavHostController) {
    composable(route = Screen.AddJar.route) {
        AddJarScreenRoot()
    }
}