package com.jack.pokemonapp.presentation.home

import com.jack.pokemonapp.domain.model.PokeRedu

data class HomeState(
    val pokemons: List<PokeRedu> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0,
    val error: String? = null,
    val isRefleshing: Boolean = false,
    val isFilterSectionVisible: Boolean = false,
    val dialog: String = "",
    val isSearching: Boolean = false,
    val cachedPokemonList: List<PokeRedu> = emptyList(),
    val isFilterByType: Boolean = false,
    val typeSelected: String = ""
)