package com.neyogiry.android.sample.pokedex.domain

import com.neyogiry.android.sample.pokedex.data.Result
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getPokemonList(): Flow<Result<List<Pokemon>>>
    fun getPokemonDetailByUrl(url: String): Flow<Result<PokemonDetail>>
}