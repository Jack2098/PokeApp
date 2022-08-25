package com.jack.pokemonapp.presentation.detail

import com.jack.pokemonapp.domain.model.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val iSLoading: Boolean = true
)