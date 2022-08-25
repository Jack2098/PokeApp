package com.jack.pokemonapp.data.source.remote.dto

import android.util.Log

data class EvolutionChain(
    val id: Int,
    val chain: EvolutionDto
)

fun EvolutionChain.toEvolution(): List<Evolution> {
    var list = listOf<Evolution>()
    var evolutionTo = chain.evolutionTo
    list = list + chain.species.toEvolution()
    while (evolutionTo.isNotEmpty()) {
        list = list + evolutionTo[0].species.toEvolution()
        evolutionTo = evolutionTo[0].evolutionTo
    }
    return list
}
