package com.neyogiry.android.sample.pokedex.domain

import com.neyogiry.android.sample.pokedex.data.Result
import kotlinx.coroutines.flow.Flow

class PokedexRepository(private val dataSource: DataSource) {

    val pokedex: Flow<Result<List<Pokemon>>> = dataSource.pokedex

    fun pokemonDetail(url: String) = dataSource.fetchPokemonDetailByUrl(url)

}