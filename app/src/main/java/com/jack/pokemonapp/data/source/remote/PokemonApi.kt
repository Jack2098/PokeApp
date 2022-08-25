package com.jack.pokemonapp.data.source.remote

import com.jack.pokemonapp.data.source.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): PokemonFullDto

    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Info

    @GET("pokemon-species/{id}")
    suspend fun getSpeciesPokemon(
        @Path("id") id: Int
    ): SpeciesReduDto

    @GET("evolution-chain/{id}")
    suspend fun getEvolution(
        @Path("id") id: Int
    ): EvolutionChain

    @GET("type/{name}")
    suspend fun getPokemonsByType(
        @Path("name") name: String
    ): PokemonListByType
}