package com.jack.pokemonapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base_stat")
    val baseStats: Int,
    val stat: Stat
)
