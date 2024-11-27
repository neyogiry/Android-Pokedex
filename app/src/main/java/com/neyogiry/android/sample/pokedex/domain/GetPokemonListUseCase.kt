package com.neyogiry.android.sample.pokedex.domain

import com.neyogiry.android.sample.pokedex.data.Result
import kotlinx.coroutines.flow.Flow

interface GetPokemonListUseCase {
    suspend operator fun invoke(): Flow<Result<List<Pokemon>>>
}