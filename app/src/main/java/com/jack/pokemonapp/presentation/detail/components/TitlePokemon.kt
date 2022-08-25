package com.jack.pokemonapp.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jack.pokemonapp.data.source.remote.dto.Types
import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.ui.theme.bgGrass1
import com.jack.pokemonapp.util.PokemonBackgrounds

@Composable
fun TitlePokemon(
    pokemon: Pokemon?,
    background: PokemonBackgrounds
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier.weight(3f),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
            ) {
                Text(
                    text = pokemon?.name?.capitalize() ?: "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp
                )
                pokemon?.types?.let {
                    GridTypes(listType = it, background)
                }
            }
        }

        Surface(
            modifier = Modifier.weight(1f),
            color = Color.Transparent
        ) {
            Text(
                text = "#${pokemon?.id ?: "00"}",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GridTypes(
    listType: List<Types>,
    background: PokemonBackgrounds
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(70.dp),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(listType) { type ->
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = background.bg1.copy(
                    alpha = 0.9f
                ),
                elevation = 2.dp,
            ) {
                Text(
                    text = type.type.name,
                    color = Color.White,
                    softWrap = false,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}