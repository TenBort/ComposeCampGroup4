package com.example.composecampgroup4.presentation.screens.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composecampgroup4.navigation.NavigationState
import com.example.composecampgroup4.navigation.Screen

fun NavGraphBuilder.homeRoute(navigationState: NavigationState) {
    composable(route = Screen.Home.route) {
        HomeScreenRoot(navigationState)
    }
}