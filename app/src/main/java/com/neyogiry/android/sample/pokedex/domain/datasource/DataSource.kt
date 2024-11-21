package com.neyogiry.android.sample.pokedex.domain.datasource

import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import com.neyogiry.android.sample.pokedex.domain.Result
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getPokemonList(): Flow<Result<List<Pokemon>>>
    fun getPokemonDetailByUrl(url: String): Flow<Result<PokemonDetail>>
}