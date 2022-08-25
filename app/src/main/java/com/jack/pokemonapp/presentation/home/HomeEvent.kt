package com.jack.pokemonapp.presentation.home

sealed class HomeEvent {
    object Reflesh: HomeEvent()
    object ToggleOrderSection: HomeEvent()
    data class ShowSnackBar(val message: String): HomeEvent()
}
