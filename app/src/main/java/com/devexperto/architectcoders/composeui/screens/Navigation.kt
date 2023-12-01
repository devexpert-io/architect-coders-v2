package com.devexperto.architectcoders.composeui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devexperto.architectcoders.composeui.screens.detail.Detail
import com.devexperto.architectcoders.composeui.screens.home.Home

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Detail : Screen("detail/{${NavArgs.ItemId.key}}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}

enum class NavArgs(val key: String) {
    ItemId("itemId")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            Home(onMovieClick = { movie ->
                navController.navigate(Screen.Detail.createRoute(movie.id))
            })
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(NavArgs.ItemId.key) { type = NavType.IntType })
        ) {
            Detail()
        }
    }
}