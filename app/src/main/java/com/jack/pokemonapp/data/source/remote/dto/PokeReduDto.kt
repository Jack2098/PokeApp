package com.jack.pokemonapp.data.source.remote.dto

import com.jack.pokemonapp.domain.model.PokeRedu

data class PokeReduDto(
    val name: String,
    val url: String
)

val patt = "([/])([0-9]+)".toRegex()

fun PokeReduDto.toPokeRedu(): PokeRedu {
    val id = patt.find(url)?.groupValues?.last()!!.toInt()
    return PokeRedu(
        id = id,
        name = name,
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg"
    )
}