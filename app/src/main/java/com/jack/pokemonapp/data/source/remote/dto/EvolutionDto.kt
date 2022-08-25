package com.jack.pokemonapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class EvolutionDto(
    @SerializedName("evolves_to")
    val evolutionTo: List<EvolutionDto>,
    val species: SpeciesEvolutionDto
)
