package com.jack.pokemonapp.domain.repository

import com.jack.pokemonapp.domain.model.PokeRedu
import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemons(page: Int, pageSize: Int): Flow<Resource<List<PokeRedu>>>

    suspend fun getPokemon(id: Int): Resource<Pokemon>

    suspend fun getAllPokemons(init: Int, limit: Int): List<PokeRedu>

    suspend fun getSpecies(id: Int)

    suspend fun getPokemonListByType(name: String): List<PokeRedu>
}