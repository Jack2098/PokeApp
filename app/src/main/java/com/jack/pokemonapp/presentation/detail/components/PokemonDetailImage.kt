package com.jack.pokemonapp.presentation.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jack.pokemonapp.R

@Composable
fun PokemonDetailImage(url: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .absoluteOffset(200.dp, 50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pokemon_icon),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .size(250.dp)
                    .alpha(0.1f)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(250.dp),
            contentAlignment = Alignment.Center
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(url)
                    .size(300)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
        }
    }
}