package com.jack.pokemonapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jack.pokemonapp.domain.use_case.GetPokemonUseCase
import com.jack.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(PokemonDetailState())
        private set

    init {
        getPokemon()
    }

    private fun getPokemon() {
        savedStateHandle.get<Int>("id")?.let { id ->
            viewModelScope.launch {
                getPokemonUseCase(id).also { result ->
                    state = when(result) {
                        is Resource.Success -> {
                            state.copy(
                                pokemon = result.data,
                                iSLoading = false
                            )
                        }
                        is Resource.Error -> {
                            state.copy(
                                iSLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            state.copy(
                                iSLoading = true
                            )
                        }
                    }
                }
            }
        }
    }
}