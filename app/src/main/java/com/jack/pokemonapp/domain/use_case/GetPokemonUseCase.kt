package com.jack.pokemonapp.domain.use_case

import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.domain.repository.PokemonRepository
import com.jack.pokemonapp.util.Resource
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(id: Int): Resource<Pokemon> {
        return repository.getPokemon(id)
    }
}