package com.neyogiry.android.sample.pokedex.di

import com.neyogiry.android.sample.pokedex.data.api.ApiClient
import com.neyogiry.android.sample.pokedex.data.api.ApiService
import com.neyogiry.android.sample.pokedex.data.datasource.RemoteDataSource
import com.neyogiry.android.sample.pokedex.data.repository.PokedexRepositoryImpl
import com.neyogiry.android.sample.pokedex.data.usecase.GetPokemonListUseCaseImpl
import com.neyogiry.android.sample.pokedex.domain.datasource.DataSource
import com.neyogiry.android.sample.pokedex.domain.repository.PokedexRepository
import com.neyogiry.android.sample.pokedex.domain.usecase.GetPokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Provides {

    @Provides
    fun providesGetPokemonListUseCase(repository: PokedexRepository): GetPokemonListUseCase {
        return GetPokemonListUseCaseImpl(repository)
    }

    @Provides
    fun providesPokedexRepository(dataSource: DataSource): PokedexRepository {
        return PokedexRepositoryImpl(dataSource)
    }

    @Provides
    fun providesDataSource(apiService: ApiService): DataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    fun provideApiService(): ApiService {
        return ApiClient.getApiService()
    }

}