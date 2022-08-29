package com.jack.pokemonapp.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jack.pokemonapp.domain.model.PokeRedu
import com.jack.pokemonapp.domain.use_case.GetPokemonListByTypeUseCase
import com.jack.pokemonapp.domain.use_case.GetPokemonsUseCase
import com.jack.pokemonapp.util.DefaultPaginator
import com.jack.pokemonapp.util.RANGE
import com.jack.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getPokemonsUseCase: GetPokemonsUseCase,
    val getPokemonListByTypeUseCase: GetPokemonListByTypeUseCase
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var cachedPokemonList = listOf<PokeRedu>()

    fun getAllPokemons() {
        viewModelScope.launch {

            val result = getPokemonsUseCase.getAllPokemons()
            when (result) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    cachedPokemonList = result.data!!
                }
            }
        }
    }

    fun searchPokemonList(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                state = state.copy(
                    isSearching = false
                )
                return@launch
            }

            val result = cachedPokemonList.filter {
                it.name.startsWith(query.trim(), ignoreCase = true) || it.id.toString() == query.trim()
            }

            state = state.copy(
                cachedPokemonList = result
            )

            state = state.copy(
                isSearching = true
            )
        }
    }

    private val pagintor = DefaultPaginator<Int, PokeRedu>(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it, error = null)
        },
        onRequest = { nextPage ->
            var result: Resource<List<PokeRedu>> = Resource.Success(emptyList())
            getPokemonsUseCase(nextPage, RANGE).collect {
                result = it
            }
            result
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(
                error = it,
                isLoading = false,
                pokemons = emptyList()
            )
            _eventFlow.emit(HomeEvent.ShowSnackBar(
                it ?: "Unknown error"
            ))
        },
        onSuccess = { pokemons, newKey ->
            state = state.copy(
                pokemons = state.pokemons + pokemons,
                page = newKey,
                endReached = pokemons.isEmpty(),
                error = null
            )
        }
    )

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.Reflesh -> {
                pagintor.reset()
                state = state.copy(
                    pokemons = emptyList(),
                    page = 0,
                    cachedPokemonList = emptyList(),
                    isSearching = false,
                    isFilterSectionVisible = false
                )
                loadNextItems()
            }
            is HomeEvent.ShowSnackBar -> {

            }
            is HomeEvent.ToggleOrderSection -> {
                state = state.copy(
                    isFilterSectionVisible = !state.isFilterSectionVisible
                )

                val dialog = "Search your pokemon or filter through the types."

                viewModelScope.launch(Dispatchers.IO) {
                    for (i in dialog) {

                        state = state.copy(
                            dialog = state.dialog + i.toString()
                        )

                        delay(200L)

                        if (!state.isFilterSectionVisible) return@launch
                    }
                }

                state = state.copy(
                    dialog = ""
                )
            }
        }
    }

    init {
        loadNextItems()
        getAllPokemons()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            delay(2000L)
            pagintor.loadNextItems()
        }
    }

    fun getPokemonListByType(type: String) {
        state = state.copy(
            isFilterByType = !state.isFilterByType
        )
        viewModelScope.launch {
            cachedPokemonList = getPokemonListByTypeUseCase(type)
            Log.d("dada", "$cachedPokemonList")
            state = state.copy(
                cachedPokemonList = cachedPokemonList,
                typeSelected = type
            )
        }
    }
}