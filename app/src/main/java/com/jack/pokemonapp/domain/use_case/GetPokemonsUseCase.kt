package com.jack.pokemonapp.domain.use_case

import android.util.Log
import com.jack.pokemonapp.domain.model.PokeRedu
import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.domain.repository.PokemonRepository
import com.jack.pokemonapp.util.AMOUNT
import com.jack.pokemonapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    operator fun invoke(page: Int, pageSize: Int): Flow<Resource<List<PokeRedu>>> {
        return repository.getPokemons(page, pageSize)
    }

    suspend fun getAllPokemons(): List<PokeRedu> {
        return repository.getAllPokemons(0, AMOUNT)
    }
}