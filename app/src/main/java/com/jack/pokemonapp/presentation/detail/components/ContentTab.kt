package com.jack.pokemonapp.presentation.detail.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jack.pokemonapp.domain.model.Pokemon
import com.jack.pokemonapp.util.parseStatToAbbr
import com.jack.pokemonapp.util.parseStatToColor

@Composable
fun About(pokemon: Pokemon?) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Species",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Height",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Weight",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Color",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Habitat",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Egg Groups",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.width(70.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "${pokemon?.name}".capitalize(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${pokemon?.height?.toDouble()?.div(10.0)} M",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${pokemon?.weight?.toDouble()?.div(10.0)} Kg",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${pokemon?.species?.color?.name}".capitalize(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = ((pokemon?.species?.habitat?.name) ?: "unknown").capitalize(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Column {
                pokemon?.species?.eggGroups?.map {
                    Text(
                        text = it.name.capitalize(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }
}

@Composable
fun BaseState(
    pokemon: Pokemon,
    animDelayPerItem: Int = 100
) {

    val maxBaseStat = remember {
        pokemon.stats.maxOf { it.baseStats }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Base stats:",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemon.stats.indices) {
            val stat = pokemon.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat.stat),
                statValue = stat.baseStats,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat.stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Evolution(pokemon: Pokemon) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 0..pokemon.evolution.lastIndex) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(pokemon.evolution[i].image)
                    .size(300)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )

            if (i < pokemon.evolution.lastIndex) {

                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(70.dp)
                )
            }

        }
    }
}

@Composable
fun Abilities(pokemon: Pokemon) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .heightIn(min = 100.dp, max = 400.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pokemon.abilities) {
            Text(
                text = it.ability.name.capitalize(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int = 0,
    statMaxValue: Int = 0,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue.div(statMaxValue.toFloat())
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}