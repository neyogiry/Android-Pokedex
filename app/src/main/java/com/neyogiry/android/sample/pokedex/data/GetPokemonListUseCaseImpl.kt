package com.neyogiry.android.sample.pokedex.data

import com.neyogiry.android.sample.pokedex.domain.GetPokemonListUseCase
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCaseImpl(
    private val pokemonRepository: PokedexRepository
) : GetPokemonListUseCase {
    override suspend fun invoke(): Flow<Result<List<Pokemon>>> {
        return pokemonRepository.getPokemonList()
    }
}