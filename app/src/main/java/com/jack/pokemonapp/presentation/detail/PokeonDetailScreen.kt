package com.jack.pokemonapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jack.pokemonapp.R
import com.jack.pokemonapp.presentation.detail.components.HeaderPokemon
import com.jack.pokemonapp.presentation.detail.components.PokemonDetailImage
import com.jack.pokemonapp.presentation.detail.components.TitlePokemon
import com.jack.pokemonapp.util.BackgroundList
import com.jack.pokemonapp.util.PokemonBackgrounds
import com.jack.pokemonapp.util.TabItem
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun PokemonDetailScreen(
    upPress: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state

    val statePage = rememberPagerState(initialPage = 0)

    val scope = rememberCoroutineScope()

    val tabs = listOf(
        TabItem.About,
        TabItem.BaseState,
        TabItem.Evolution,
        TabItem.Abilities
    )

    val pokemonBackgrounds = BackgroundList.pokemonBackgrounds

    val background = state.pokemon?.let { pokemon ->
        pokemonBackgrounds.find {
            it.name == pokemon.types[0].type.name
        }
    } ?: PokemonBackgrounds.Normal


    if (state.iSLoading) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pokemon_icon),
                    contentDescription = "",
                    tint = Color.Red.copy(
                        alpha = 0.6f
                    ),
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    } else {
        Surface(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            background.bg1,
                            background.bg2
                        )
                    )
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HeaderPokemon(upPress)
                TitlePokemon(state.pokemon, background)
                state.pokemon?.let { PokemonDetailImage(url = it.image) }
                Spacer(modifier = Modifier.height(20.dp))
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 20.dp),
                    color = Color.White,
                    thickness = 2.dp,
                )
                TabRow(
                    selectedTabIndex = statePage.currentPage,
                    backgroundColor = Color.Transparent,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    contentColor = Color.White
                ) {
                    tabs.forEachIndexed { index, tab ->

                        Text(
                            text = tab.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            softWrap = false,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clickable {
                                    scope.launch {
                                        statePage.animateScrollToPage(index)
                                    }
                                },
                            textAlign = TextAlign.Center
                        )

//                    Tab(
//                        selected = statePage.currentPage == index,
//                        onClick = {
//                            scope.launch {
//                                statePage.animateScrollToPage(index)
//                            }
//                        },
//                        modifier = Modifier.width(10.dp),
//                        text = {
//                            Text(
//                                modifier = Modifier.fillMaxWidth(),
//                                text = tab.title,
//                                color = Color.White,
//                                fontWeight = FontWeight.Bold,
//                                softWrap = false,
//                            )
//                        }
//                    )
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    color = Color.White.copy(
                        alpha = 0.1f
                    )
                ) {
                    HorizontalPager(
                        count = tabs.size,
                        state = statePage
                    ) { page ->
                        state.pokemon?.let { tabs[page].screen(it) }
                    }
                }
            }
        }
    }
}