package com.jack.pokemonapp.util

import androidx.compose.runtime.Composable
import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.presentation.detail.components.Abilities
import com.jack.pokemonapp.presentation.detail.components.About
import com.jack.pokemonapp.presentation.detail.components.BaseState
import com.jack.pokemonapp.presentation.detail.components.Evolution

sealed class TabItem(var title: String, var screen: @Composable (Pokemon) -> Unit) {
    object About: TabItem("About", { About(pokemon = it) })
    object BaseState: TabItem("Base State", { BaseState(pokemon = it) })
    object Evolution: TabItem("Evolution", { Evolution(pokemon = it) })
    object Abilities: TabItem("Abilities", { Abilities(pokemon = it) })
}
