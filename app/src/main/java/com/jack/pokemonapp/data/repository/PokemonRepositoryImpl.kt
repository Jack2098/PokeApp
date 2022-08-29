package com.jack.pokemonapp.data.repository

import android.util.Log
import com.jack.pokemonapp.data.source.remote.PokemonApi
import com.jack.pokemonapp.data.source.remote.dto.*
import com.jack.pokemonapp.domain.model.PokeRedu
import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.domain.repository.PokemonRepository
import com.jack.pokemonapp.util.AMOUNT
import com.jack.pokemonapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {

    override fun getPokemons(page: Int, pageSize: Int): Flow<Resource<List<PokeRedu>>> = flow {
        val startingIndex = page * pageSize
        try {
            if (startingIndex + pageSize <= AMOUNT) {
                val pokemonList = api.getAllPokemons(
                    offset = startingIndex,
                    limit = pageSize
                ).results.map { it.toPokeRedu() }
                emit(Resource.Success(pokemonList))
            } else if (page <= AMOUNT / pageSize) {
                val pokemonList = api.getAllPokemons(
                    offset = startingIndex,
                    limit = AMOUNT - startingIndex
                ).results.map { it.toPokeRedu() }
                emit(Resource.Success(pokemonList))
            } else Resource.Success(emptyList<PokeRedu>())

//            if (startingIndex + pageSize <= AMOUNT) {
//                val pokemonList = List(pageSize) {
//                    val id = startingIndex + 1 + it
//                    api.getPokemon(id).toListPokemon()
//                }
//                emit(Resource.Success(pokemonList))
//            } else if (page <= AMOUNT / pageSize) {
//                val pokemonList = List(AMOUNT - startingIndex + 1) {
//                    val id = startingIndex++
//                    api.getPokemon(id).toListPokemon()
//                }
//                emit(Resource.Success(pokemonList))
//            } else Resource.Success(emptyList<Pokemon>())
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oop, something went wrong",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Oop, Couldn't reach server, check your internet connection",
                    data = null
                )
            )
        }

    }

    override suspend fun getPokemon(id: Int): Resource<Pokemon> {
        return try {
            val response = api.getPokemon(id)
            val specie = api.getSpeciesPokemon(response.id)
            val evolutionId = patt.find(specie.evolution.url)?.groupValues?.last()!!.toInt()
            val evolution = api.getEvolution(evolutionId).toEvolution()

            Resource.Success(
                response.toPokemon(
                    species = specie.toSpecies(),
                    evolution = evolution
                )
            )
        } catch (e: HttpException) {
            Resource.Error(
                message = "Oop, something went wrong",
                data = null
            )
        } catch (e: IOException) {
            Resource.Error(
                message = "Oop, Couldn't reach server, check your internet connection",
                data = null
            )
        }
    }

    override suspend fun getAllPokemons(init: Int, limit: Int): Resource<List<PokeRedu>> {

        return try {
            val response = api.getAllPokemons(offset = init, limit = limit).results.map { it.toPokeRedu() }
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(
                message = "Oop, something went wrong",
                data = null
            )
        } catch (e: IOException) {
            Resource.Error(
                message = "Oop, Couldn't reach server, check your internet connection",
                data = null
            )
        }


    }

    override suspend fun getSpecies(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonListByType(name: String): List<PokeRedu> {
        return api.getPokemonsByType(name).let { pokeList ->
            pokeList.pokemon
                .map { it.pokemon.toPokeRedu() }
                .filter { it.id <= AMOUNT }
        } ?: emptyList<PokeRedu>()
    }
}