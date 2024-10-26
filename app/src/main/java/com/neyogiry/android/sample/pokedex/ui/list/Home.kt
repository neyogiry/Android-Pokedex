package com.neyogiry.android.sample.pokedex.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.ui.ErrorScreen
import com.neyogiry.android.sample.pokedex.ui.LoadingScreen
import com.neyogiry.android.sample.pokedex.ui.theme.Pokedex
import com.neyogiry.android.sample.pokedex.util.Image
import com.neyogiry.android.sample.pokedex.util.ImageHelper

@ExperimentalFoundationApi
@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    onItemClick: (Pokemon) -> Unit,
) {
    val viewState by viewModel.state.collectAsState()
    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.showError) {
        ErrorScreen(onRetry = { viewModel.retry() })
    } else {
        HomeContent(
            viewModel = viewModel,
            list = viewState.pokemonList,
            onItemClick = onItemClick,
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    list: List<Pokemon>,
    onItemClick: (Pokemon) -> Unit,
) {
    if (list.isNotEmpty()) {
        Scaffold(
            topBar = { PokedexAppBar() },
        ) { paddingValues ->
            PokemonList(
                viewModel = viewModel,
                list = list,
                paddingValues = paddingValues,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun PokedexAppBar() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Pokedex)
    TopAppBar(
        backgroundColor = Pokedex,
    ) {
        Text(
            text = "Pokedex",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun PokemonList(
    viewModel: HomeViewModel,
    list: List<Pokemon>,
    paddingValues: PaddingValues,
    onItemClick: (Pokemon) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = paddingValues
    ) {
        itemsIndexed(list) { index, item ->
            PokemonItem(
                pokemon = item,
                onUpdatePokemonColor = { viewModel.savePokemonColor(index, it) },
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onUpdatePokemonColor: (Color) -> Unit,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(40.dp)
    pokemon.averageColor?.let {
        PokemonItemContent(pokemon = pokemon, shape = shape, onClick = onClick)
    } ?: run {
        PokemonItemContent(pokemon = pokemon, shape = shape, onUpdatePokemonColor = onUpdatePokemonColor, onClick = onClick)
    }
}

@Composable
fun PokemonItemContent(
    pokemon: Pokemon,
    shape: Shape,
    onUpdatePokemonColor: (Color) -> Unit,
    onClick: () -> Unit,
) {
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp)
            .border(width = 1.dp, color = Color.Gray, shape = shape)
            .background(color = backgroundColor, shape = shape)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            url = ImageHelper.pokemonImageUrl(pokemon.url),
            modifier = Modifier.weight(1f),
            averageColor = { backgroundColor = it; onUpdatePokemonColor(it) }
        )
        Text(
            text = pokemon.name,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun PokemonItemContent(
    pokemon: Pokemon,
    shape: Shape,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp)
            .border(width = 1.dp, color = Color.Gray, shape = shape)
            .background(color = pokemon.averageColor ?: Color.Transparent, shape = shape)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            url = ImageHelper.pokemonImageUrl(pokemon.url),
            modifier = Modifier.weight(1f),
        )
        Text(
            text = pokemon.name,
            fontSize = 16.sp,
        )
    }
}