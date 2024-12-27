package com.neyogiry.android.sample.pokedex.data.api

import com.neyogiry.android.sample.pokedex.data.PokedexResponse
import com.neyogiry.android.sample.pokedex.data.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * REST API access points
 */
interface ApiService {

    @GET("pokemon")
    suspend fun pokemonList() : Response<PokedexResponse>

    @GET("pokemon/{id}")
    suspend fun pokemonDetailById(@Path("id") id: Int) : Response<PokemonDetailResponse>

    @GET("pokemon/{name}")
    suspend fun pokemonDetailByName(@Path("name") name: String) : Response<PokemonDetailResponse>

    @GET
    suspend fun pokemonDetailByUrl(@Url url: String) : Response<PokemonDetailResponse>

}