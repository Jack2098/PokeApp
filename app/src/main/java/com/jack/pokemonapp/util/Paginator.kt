package com.jack.pokemonapp.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}