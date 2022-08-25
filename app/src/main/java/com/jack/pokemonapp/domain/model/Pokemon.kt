package com.jack.pokemonapp.domain.model

import com.jack.pokemonapp.data.source.remote.dto.*

data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val abilities: List<Abilities>,
    val height: Int,
    val weight: Int,
    val stats: List<Stats>,
    val types: List<Types>,
    val species: Species,
    val evolution: List<Evolution>
)
