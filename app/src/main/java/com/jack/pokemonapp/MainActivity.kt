package com.jack.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jack.pokemonapp.presentation.PokemonApp
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonApp()
        }
    }
}