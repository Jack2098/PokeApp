package com.jack.pokemonapp.data.source.remote.dto

import com.jack.rickyandmorty.data.source.remote.dto.Sprites

data class PokemonDto(
    val id: Int,
    val name: String,
    val species: SpeciesEvolutionDto,
    val sprites: Sprites
)
