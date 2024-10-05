package com.neyogiry.android.sample.pokedex.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.neyogiry.android.sample.pokedex.ui.theme.PokedexTheme

@ExperimentalFoundationApi
@Composable
fun PokedexApp() {
    PokedexTheme {
        val navController = rememberNavController()
        PokedexNavGraph(navController = navController)
    }
}