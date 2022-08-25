package com.jack.pokemonapp.data.source.remote.dto

data class Species(
    val name: String,
    val color: Color,
    val eggGroups: List<EggGroup>,
    val habitat: Habitat?,
)