package com.jack.pokemonapp.data.source.remote.dto

import com.jack.pokemonapp.domain.model.Pokemon

data class PokemonFullDto(
    val id: Int,
    val name: String,
    val abilities: List<Abilities>,
    val height: Int,
    val weight: Int,
    val stats: List<Stats>,
    val types: List<Types>,
)

fun PokemonFullDto.toPokemon(species: Species, evolution: List<Evolution>): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg",
        abilities = abilities,
        height = height,
        weight = weight,
        stats =  stats,
        types = types,
        species = species,
        evolution = evolution
    )
}