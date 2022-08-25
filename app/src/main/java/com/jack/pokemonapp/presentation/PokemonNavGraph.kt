package com.jack.pokemonapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jack.pokemonapp.presentation.detail.PokemonDetailScreen

@ExperimentalPagerApi
@Composable
fun PokemonNavGraph(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onItemClicked = { navigateToDetail(it) }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            PokemonDetailScreen(
                upPress = navigateToHome
            )
        }
    }
}