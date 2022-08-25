package com.jack.pokemonapp.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jack.pokemonapp.presentation.home.PokemonActions
import com.jack.pokemonapp.presentation.home.PokemonNavGraph
import com.jack.pokemonapp.ui.theme.PokemonAppTheme

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun PokemonApp() {
    PokemonAppTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            PokemonActions(navController)
        }

        PokemonNavGraph(
            navController = navController,
            navigateToHome = navigationActions.navigateToHome,
            navigateToDetail = navigationActions.navigateToDetail
        )
    }
}