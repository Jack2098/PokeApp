package com.jack.pokemonapp.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderPokemon(upPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = upPress) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(32.dp, 32.dp)
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(32.dp, 32.dp)
            )
        }
    }
}