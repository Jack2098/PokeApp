package com.jack.pokemonapp.domain.model

import com.jack.rickyandmorty.data.source.remote.dto.Other

data class Sprites(
    val back_default: String,
    val back_shiny: String,
    val front_default: String,
    val front_shiny: String,
    val other: Other,
)