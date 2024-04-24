package com.example.composecampgroup4.presentation.screens.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composecampgroup4.navigation.Screen

fun NavGraphBuilder.homeRoute(navController: NavHostController) {
    composable(route = Screen.Home.route) {
        HomeScreenRoot()
    }
}