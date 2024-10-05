package com.neyogiry.android.sample.pokedex.data

import com.neyogiry.android.sample.pokedex.domain.DataSource
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(
    private val apiService: ApiService = ApiClient.getApiService()
) : DataSource {

    override val pokedex: Flow<Result<List<Pokemon>>>
        get() = flow {
            val response = apiService.pokemonList()
            if(response.isSuccessful) {
                val pokemonListResponse = response.body()?.results
                val list = ArrayList<Pokemon>()
                pokemonListResponse?.forEach { item ->
                    list.add(
                        Pokemon(item.name ?: "", item.url ?: "")
                    )
                }
                emit(Result.Success(list))
            } else {
                emit(Result.Error(Exception()))
            }

        }

    override fun fetchPokemonDetailByUrl(url: String): Flow<Result<PokemonDetail>> {
        return flow {
            val response = apiService.pokemonDetailByUrl(url)
            if (response.isSuccessful) {
                val pokemonResponse = response.body()
                pokemonResponse?.let { pokemon ->
                    emit(
                        Result.Success(PokemonDetail(name = pokemon.name ?: "", height = pokemon.height ?: 0, weight = pokemon.weight ?: 0))
                    )
                } ?: kotlin.run {
                    emit(Result.Error(Exception()))
                }
            } else {
                emit(Result.Error(Exception()))
            }
        }
    }

}