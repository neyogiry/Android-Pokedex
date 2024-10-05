package com.neyogiry.android.sample.pokedex.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.neyogiry.android.sample.pokedex.data.RemoteDataSource
import com.neyogiry.android.sample.pokedex.data.Result
import com.neyogiry.android.sample.pokedex.domain.PokedexRepository
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val url: String,
    private val repository: PokedexRepository = PokedexRepository(RemoteDataSource()),
) : ViewModel() {

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(PokemonDetailViewState())

    val state: StateFlow<PokemonDetailViewState>
        get() = _state

    init {
        fetchPokemonDetails()
    }

    private fun fetchPokemonDetails() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            repository.pokemonDetail(url)
                .flowOn(Dispatchers.IO)
                .catch {
                    _state.update { it.copy(pokemon = null, showError = true, loading = false) }
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> _state.update { it.copy(pokemon = result.data, showError = false, loading = false) }
                        is Result.Error -> _state.update { it.copy(pokemon = null, showError = true, loading = false) }
                    }
                }
        }
    }

    fun retry() {
        fetchPokemonDetails()
    }

    companion object {
        fun provideFactory(
            url: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonDetailViewModel(url) as T
            }
        }
    }

}

data class PokemonDetailViewState(
    val pokemon: PokemonDetail? = null,
    val showError: Boolean = false,
    val loading: Boolean = false,
)