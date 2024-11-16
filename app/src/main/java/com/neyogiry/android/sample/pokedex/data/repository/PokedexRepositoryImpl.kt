package com.neyogiry.android.sample.pokedex.data.repository

import com.neyogiry.android.sample.pokedex.data.Result
import com.neyogiry.android.sample.pokedex.domain.DataSource
import com.neyogiry.android.sample.pokedex.domain.repository.PokedexRepository
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import kotlinx.coroutines.flow.Flow

class PokedexRepositoryImpl(
    private val dataSource: DataSource
) : PokedexRepository {
    override suspend fun getPokemonList(): Flow<Result<List<Pokemon>>> {
        return dataSource.pokedex
    }

    override suspend fun getPokemonDetail(url: String): Flow<Result<PokemonDetail>> {
        return dataSource.fetchPokemonDetailByUrl(url)
    }
}