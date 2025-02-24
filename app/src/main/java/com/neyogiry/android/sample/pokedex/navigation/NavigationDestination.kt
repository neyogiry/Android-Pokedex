package com.neyogiry.android.sample.pokedex.navigation

import kotlinx.serialization.Serializable

@Serializable
object PokemonList

@Serializable
data class PokemonDetail(val url: String, val averageColor: String?)