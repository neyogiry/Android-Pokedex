package com.neyogiry.android.sample.pokedex.data.repository

import com.neyogiry.android.sample.pokedex.domain.Result
import com.neyogiry.android.sample.pokedex.domain.datasource.DataSource
import com.neyogiry.android.sample.pokedex.domain.repository.PokedexRepository
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val dataSource: DataSource
) : PokedexRepository {
    override suspend fun getPokemonList(): Flow<Result<List<Pokemon>>> {
        return dataSource.getPokemonList()
    }

    override suspend fun getPokemonDetail(url: String): Flow<Result<PokemonDetail>> {
        return dataSource.getPokemonDetailByUrl(url)
    }
}