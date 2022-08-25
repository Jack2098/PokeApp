package com.jack.pokemonapp.data.source.remote.dto

data class SpeciesEvolutionDto(
    val name: String,
    val url: String,
)

fun SpeciesEvolutionDto.toEvolution(): Evolution {
    val id = patt.find(url)?.groupValues?.last()!!.toInt()
    return Evolution(
        id = id,
        name = name,
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg"
    )
}