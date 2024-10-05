package com.neyogiry.android.sample.pokedex.domain

import com.neyogiry.android.sample.pokedex.data.Result
import kotlinx.coroutines.flow.Flow

interface DataSource {
    val pokedex: Flow<Result<List<Pokemon>>>
    fun fetchPokemonDetailByUrl(url: String): Flow<Result<PokemonDetail>>
}