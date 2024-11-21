package com.neyogiry.android.sample.pokedex.domain.repository

import com.neyogiry.android.sample.pokedex.domain.Result
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {
    suspend fun getPokemonList(): Flow<Result<List<Pokemon>>>
    suspend fun getPokemonDetail(url: String): Flow<Result<PokemonDetail>>
}