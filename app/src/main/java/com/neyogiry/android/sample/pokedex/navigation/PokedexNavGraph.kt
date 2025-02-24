package com.neyogiry.android.sample.pokedex.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.neyogiry.android.sample.pokedex.ui.detail.PokemonDetailViewModel
import com.neyogiry.android.sample.pokedex.ui.detail.PokemonDetails
import com.neyogiry.android.sample.pokedex.ui.list.Home
import com.neyogiry.android.sample.pokedex.ui.list.HomeViewModel

@ExperimentalFoundationApi
@Composable
fun PokedexNavGraph(
    navController: NavHostController = rememberNavController()
) {
    
    NavHost(
        navController = navController,
        startDestination = PokemonList,
    ) {
        composable<PokemonList> {
            Home(
                viewModel = hiltViewModel<HomeViewModel>()
            ) { pokemon ->
                navController.navigate(
                    PokemonDetail(url = pokemon.url, averageColor = pokemon.averageColor?.let { "#${Integer.toHexString(it.toArgb())}" })
                )
            }
        }
        composable<PokemonDetail> { backStackEntry ->
            val pokemonDetail: PokemonDetail = backStackEntry.toRoute()
            PokemonDetails(
                url = pokemonDetail.url,
                averageColor = pokemonDetail.averageColor,
                viewModel = hiltViewModel<PokemonDetailViewModel>(),
                onBackPressed = { navController.popBackStack() }
            )
        }
    }

}