package com.neyogiry.android.sample.pokedex.data

import com.neyogiry.android.sample.pokedex.domain.DataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RemoteDataSourceTest {

    private val apiService: ApiService = mock()
    private lateinit var dataSource: DataSource

    @Before
    fun setUp() {
        dataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `given successful API response when getPokemonList is called then it emits Success with correct data`() = runTest {
        val response = Response.success(getResponse())
        whenever(apiService.pokemonList())
            .thenReturn(response)

        val result = dataSource.getPokemonList().first()

        verify(apiService).pokemonList()
        assertTrue(result is Result.Success)
        assertEquals(1, result.data.size)
    }

    @Test
    fun `given error API response when getPokemonList is called then it emits Error`() = runTest {
        val response = Response.error<PokedexResponse>(500, "".toResponseBody())
        whenever(apiService.pokemonList())
            .thenReturn(response)

        val result = dataSource.getPokemonList().first()

        verify(apiService).pokemonList()
        assertTrue(result is Result.Error)
    }

    private fun getResponse(): PokedexResponse {
        return PokedexResponse(
            count = null,
            next = null,
            results = listOf(
                PokemonResponse(name = "Bulbasaur", url = "")
            )
        )
    }
}