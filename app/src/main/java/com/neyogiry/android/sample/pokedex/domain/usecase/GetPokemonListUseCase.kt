package com.neyogiry.android.sample.pokedex.domain.usecase

import com.neyogiry.android.sample.pokedex.domain.Pokemon
import com.neyogiry.android.sample.pokedex.domain.Result
import kotlinx.coroutines.flow.Flow

interface GetPokemonListUseCase {
    suspend operator fun invoke(): Flow<Result<List<Pokemon>>>
}