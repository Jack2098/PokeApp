package com.jack.pokemonapp.presentation.home

import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jack.pokemonapp.R
import com.jack.pokemonapp.presentation.home.components.PokemonCard
import com.jack.pokemonapp.util.BackgroundList
import com.jack.pokemonapp.util.PokemonBackgrounds

@Composable
fun HomeScreen(
    onItemClicked: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefleshing
    )

    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()

    val textSearch = remember {
        mutableStateOf("")
    }

    val textFilterByType = remember {
        mutableStateOf("")
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
    ) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            content = {
                SwipeRefresh(
                    modifier = Modifier.padding(top = 20.dp),
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.onEvent(
                            HomeEvent.Reflesh
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HomeTopBar(viewModel = viewModel)
                        AnimatedVisibility(
                            visible = state.isFilterSectionVisible,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            MenuFilter(
                                viewModel = viewModel,
                                textFilterByType =  textFilterByType,
                                textSearch = textSearch
                            )
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(10.dp)
                        ) {
                            if (!state.isSearching && textFilterByType.value === "") {
                                items(state.pokemons.size) { i ->
                                    val item = state.pokemons[i]
                                    if (i >= state.pokemons.size - 1 && !state.endReached && !state.isLoading) {
                                        viewModel.loadNextItems()
                                    }
                                    PokemonCard(
                                        modifier = Modifier.size(250.dp),
                                        pokemon = item,
                                        onItemClicked = { id ->
                                            onItemClicked(id)
                                        }
                                    )
                                }
                            } else {
                                items(state.cachedPokemonList.size) { i ->
                                    val item = state.cachedPokemonList[i]
                                    PokemonCard(
                                        modifier = Modifier.size(250.dp),
                                        pokemon = item,
                                        onItemClicked = { id ->
                                            onItemClicked(id)
                                        }
                                    )
                                }
                            }

                            item {
                                if (state.isLoading) {
                                    ShowLoading()
                                }
                            }

                        }
                        state.error?.let {
                            ShowError(error = it)
                        }
                    }

                }

            },
        )
    }
}

@Composable
fun HomeTopBar(
    viewModel: HomeViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title_app),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        IconButton(
            onClick = {
                viewModel.onEvent(HomeEvent.ToggleOrderSection)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Short",
                tint = Color.White
            )
        }
    }
}

@Composable
fun MenuFilter(
    viewModel: HomeViewModel,
    textFilterByType: MutableState<String>,
    textSearch: MutableState<String>,
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.profesor_oak),
                contentDescription = "Oak",
                modifier = Modifier.size(150.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(30.dp))
                    .widthIn(min = 200.dp)
                    .heightIn(min = 50.dp)
            ) {
                Text(
                    text = state.dialog,
                    fontSize = 16.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(
            hint = "Search...",
            modifier = Modifier
                .fillMaxWidth(),
            textSearch = textSearch
        ) {
            viewModel.searchPokemonList(it)
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(BackgroundList.pokemonBackgrounds) { type->
                ChipTypes(type, textFilterByType) { viewModel.getPokemonListByType(it) }
            }
        }
    }

}

@Composable
fun ChipTypes(
    type: PokemonBackgrounds,
    textFilterByType: MutableState<String>,
    onTypeClicked: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = type.bg1,
        elevation = 4.dp,
        modifier = Modifier.clickable {

            if (textFilterByType.value === type.name) {
                textFilterByType.value = ""
            } else {
                textFilterByType.value = type.name
                onTypeClicked(type.name.lowercase())
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = type.name.capitalize(),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
            if (textFilterByType.value === type.name) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Type selected",
                    tint = Color.Green
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    textSearch: MutableState<String>,
    onSearch: (String) -> Unit = {},
) {

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = textSearch.value,
            onValueChange = {
                textSearch.value = it
                onSearch(textSearch.value)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
        )
        if (isHintDisplayed && textSearch.value.isEmpty()) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}


@Composable
fun ShowLoading() {
    Row(
        modifier = Modifier
            .absoluteOffset(x = 150.dp),
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowError(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}