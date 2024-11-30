package com.neyogiry.android.sample.pokedex.ui.list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neyogiry.android.sample.pokedex.data.GetPokemonListUseCaseImpl
import com.neyogiry.android.sample.pokedex.data.repository.PokedexRepositoryImpl
import com.neyogiry.android.sample.pokedex.data.datasource.RemoteDataSource
import com.neyogiry.android.sample.pokedex.domain.GetPokemonListUseCase
import com.neyogiry.android.sample.pokedex.domain.Result
import com.neyogiry.android.sample.pokedex.domain.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase = GetPokemonListUseCaseImpl(PokedexRepositoryImpl(RemoteDataSource())),
) : ViewModel() {

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        getPokedex()
    }

    private fun getPokedex() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            getPokemonListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _state.update { it.copy(showError = true, loading = false) }
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> _state.update { it.copy(pokemonList = result.data, showError = false, loading = false) }
                        is Result.Error -> _state.update { it.copy(showError = true, loading = false) }
                    }

                }
        }
    }

    fun retry() {
        getPokedex()
    }

    fun savePokemonColor(position: Int, color: Color) {
        _state.value.pokemonList[position].averageColor = color
    }

}

data class HomeViewState(
    val pokemonList: List<Pokemon> = emptyList(),
    val showError: Boolean = false,
    val loading: Boolean = false,
)