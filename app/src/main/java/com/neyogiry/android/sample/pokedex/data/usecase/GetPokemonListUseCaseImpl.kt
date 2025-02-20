package com.neyogiry.android.sample.pokedex.data.usecase

import com.neyogiry.android.sample.pokedex.domain.usecase.GetPokemonListUseCase
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.Result
import com.neyogiry.android.sample.pokedex.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokedexRepository
) : GetPokemonListUseCase {
    override suspend fun invoke(): Flow<Result<List<Pokemon>>> {
        return pokemonRepository.getPokemonList()
    }
}