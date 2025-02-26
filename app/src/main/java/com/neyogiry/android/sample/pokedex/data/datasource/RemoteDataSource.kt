package com.neyogiry.android.sample.pokedex.data.datasource

import com.neyogiry.android.sample.pokedex.data.api.ApiService
import com.neyogiry.android.sample.pokedex.domain.datasource.DataSource
import com.neyogiry.android.sample.pokedex.domain.Failure
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import com.neyogiry.android.sample.pokedex.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : DataSource {

    override fun getPokemonList(): Flow<Result<List<Pokemon>>> {
        return flow {
            val response = apiService.pokemonList()
            if (response.isSuccessful) {
                response.body()?.let {
                    val pokemonListResponse = it.results
                    val list = ArrayList<Pokemon>()
                    pokemonListResponse?.let {
                        pokemonListResponse.forEach { item ->
                            list.add(
                                Pokemon(item.name ?: "", item.url ?: "")
                            )
                        }
                        emit(Result.Success(list))
                    } ?: emit(Result.Error(Failure.DataError))
                } ?: emit(Result.Error(Failure.DataError))
            } else {
                emit(Result.Error(Failure.ServerError))
            }

        }
    }

    override fun getPokemonDetailByUrl(url: String): Flow<Result<PokemonDetail>> {
        return flow {
            val response = apiService.pokemonDetailByUrl(url)
            if (response.isSuccessful) {
                val pokemonResponse = response.body()
                pokemonResponse?.let { pokemon ->
                    emit(
                        Result.Success(
                            PokemonDetail(
                                id = pokemon.id,
                                name = pokemon.name ?: "",
                                height = pokemon.height ?: 0,
                                weight = pokemon.weight ?: 0
                            )
                        )
                    )
                } ?: emit(Result.Error(Failure.DataError))
            } else {
                emit(Result.Error(Failure.ServerError))
            }
        }
    }

}