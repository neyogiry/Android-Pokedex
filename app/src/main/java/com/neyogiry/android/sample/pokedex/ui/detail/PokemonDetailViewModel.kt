package com.neyogiry.android.sample.pokedex.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neyogiry.android.sample.pokedex.domain.Result
import com.neyogiry.android.sample.pokedex.domain.repository.PokedexRepository
import com.neyogiry.android.sample.pokedex.domain.PokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokedexRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonDetailViewState())

    val state: StateFlow<PokemonDetailViewState>
        get() = _state

    fun fetchPokemonDetails(url: String) {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            repository.getPokemonDetail(url)
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

    fun retry(url: String) {
        fetchPokemonDetails(url)
    }

}

data class PokemonDetailViewState(
    val pokemon: PokemonDetail? = null,
    val showError: Boolean = false,
    val loading: Boolean = false,
)