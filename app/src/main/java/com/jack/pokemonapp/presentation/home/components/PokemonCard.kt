package com.jack.pokemonapp.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jack.pokemonapp.domain.model.PokeRedu

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: PokeRedu,
    onItemClicked: (Int) -> Unit
) {
    Card(
        modifier = modifier.clickable { onItemClicked(pokemon.id) },
        elevation = 3.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color(0xFF16181A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            PokemonImage(image = pokemon.image)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pokemon.name
                        .split('-')
                        .joinToString(" ")
                        .capitalize(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = "#${pokemon.id}",
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}