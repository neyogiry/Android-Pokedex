package com.neyogiry.android.sample.pokedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neyogiry.android.sample.pokedex.R
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import com.neyogiry.android.sample.pokedex.ui.ErrorScreen
import com.neyogiry.android.sample.pokedex.ui.LoadingScreen
import com.neyogiry.android.sample.pokedex.ui.theme.Pokedex
import com.neyogiry.android.sample.pokedex.util.Image
import com.neyogiry.android.sample.pokedex.util.ImageHelper

@Composable
fun PokemonDetails(
    pokemon: Pokemon,
    viewModel: PokemonDetailViewModel,
    onBackPressed: () -> Unit,
) {
    val viewState by viewModel.state.collectAsState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(pokemon.averageColor ?: Pokedex)
    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.showError) {
        ErrorScreen(onRetry = { viewModel.retry(pokemon.url) })
    } else {
        viewState.pokemon?.let { pokemonDetail ->
            PokemonDetailContent(pokemon = pokemonDetail, pokemonColor = pokemon.averageColor ?: Pokedex, url = pokemon.url, onBackPressed = onBackPressed)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetails(pokemon.url)
    }
}

@Composable
private fun PokemonDetailContent(
    pokemon: PokemonDetail,
    pokemonColor: Color,
    url: String,
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = pokemonColor)
    ) {
        Column(modifier = Modifier.fillMaxSize(),) {
            Header(onBackPressed = onBackPressed)
            Details(pokemon = pokemon, url = ImageHelper.pokemonImageUrl(url))
        }
    }
}

@Composable
fun Header(
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(270.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_expand_more),
            contentDescription = null,
            modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onBackPressed() },
            tint = Color.White
        )

    }
}

@Composable
fun PokemonImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    Image(
        url = imageUrl,
        modifier = modifier
            .size(200.dp)
    )
}

@Composable
fun PokemonImage(
    imageUrl: String,
    headerBackgroundColor: (Color) -> Unit,
) {
    Image(
        url = imageUrl,
        modifier = Modifier
            .size(200.dp),
        averageColor = { headerBackgroundColor(it) }
    )
}

@Composable
fun Details(
    pokemon: PokemonDetail,
    url: String,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .offset(y = (-150).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            PokemonImage(imageUrl = url)
            Text(
                text = pokemon.name,
                fontSize = 24.sp,
                modifier = Modifier
                    .wrapContentSize()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                //Weight
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format("%.1f Kg", pokemon.weight/10f),
                        fontSize = 24.sp,
                        modifier = Modifier
                            .wrapContentHeight()
                    )
                    Text(
                        text = "Weight",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .wrapContentHeight()
                    )
                }

                //Height
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = String.format("%.1f m", pokemon.height/10f),
                        fontSize = 24.sp,
                        modifier = Modifier
                            .wrapContentHeight()
                    )
                    Text(
                        text = "Height",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .wrapContentHeight()
                    )
                }
            }

        }
    }

}

@Preview
@Composable
private fun PokemonDetailsPreview() {
    PokemonDetailContent(
        pokemon = PokemonDetail(name = "Bulbasaur", height = 7, weight = 69),
        pokemonColor = Color(0xFF069494),
        url = "https://pokeapi.co/api/v2/pokemon/1/",
        onBackPressed = {}
    )
}