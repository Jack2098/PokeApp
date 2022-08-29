package com.jack.pokemonapp.util

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Resource<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (String?) -> Unit,
    private inline val onSuccess: suspend (item: List<Item>, newKey: Key) -> Unit
): Paginator<Key, Item> {

    private var currencyKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) return
        isMakingRequest = true

        val result = onRequest(currencyKey)
        isMakingRequest = false
        val items = when(result) {
            is Resource.Success -> result.data ?: emptyList()
            is Resource.Error -> {
                onLoadUpdated(false)
                onError(result.message)
                return
            }
            is Resource.Loading -> {
                onLoadUpdated(true)
                emptyList()
            }
        }
        currencyKey = getNextKey(items)
        onSuccess(items, currencyKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currencyKey = initialKey
    }
}