package com.jack.pokemonapp.domain.use_case

import com.jack.pokemonapp.domain.model.PokeRedu
import com.jack.pokemonapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListByTypeUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(type: String): List<PokeRedu> {
        return repository.getPokemonListByType(type)
    }
}