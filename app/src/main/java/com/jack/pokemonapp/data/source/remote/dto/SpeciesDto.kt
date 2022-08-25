package com.jack.pokemonapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class SpeciesReduDto(
    val name: String,
    val color: Color,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroup>,
    val habitat: Habitat,
    @SerializedName("evolution_chain")
    val evolution: EvolutionReduDto
)

fun SpeciesReduDto.toSpecies(): Species {
    return Species(
        name = name,
        color = color,
        eggGroups = eggGroups,
        habitat = habitat
    )
}