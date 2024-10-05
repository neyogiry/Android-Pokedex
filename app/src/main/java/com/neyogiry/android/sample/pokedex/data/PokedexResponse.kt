package com.neyogiry.android.sample.pokedex.data

import com.google.gson.annotations.SerializedName

data class PokedexResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("results") val results: List<PokemonResponse>?,
)

data class PokemonResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?,
)