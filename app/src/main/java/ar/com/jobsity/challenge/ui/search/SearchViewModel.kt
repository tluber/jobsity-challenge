package ar.com.jobsity.challenge.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.jobsity.challenge.data.repository.TvMazeRepository
import ar.com.jobsity.challenge.network.response.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data class Success(
        val showList: List<Show>
    ) : SearchUiState

    data class Error(val message: String) : SearchUiState
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tvMazeRepository: TvMazeRepository
) : ViewModel() {

    private val mSearchUiState: MutableStateFlow<SearchUiState?> = MutableStateFlow(null)
    val searchUiState: StateFlow<SearchUiState?> = mSearchUiState.asStateFlow()

    fun getShows(query: String) {
        viewModelScope.launch {
            mSearchUiState.value = SearchUiState.Loading
            tvMazeRepository.searchShows(query)
                .onSuccess { searchShows ->
                    if (searchShows.isNotEmpty()) {
                        val shows = searchShows.map { it.show }
                        mSearchUiState.value = SearchUiState.Success(shows)
                    } else {
                        mSearchUiState.value = SearchUiState.Success(listOf())
                    }
                }.onFailure {
                    Timber.e(it)
                    mSearchUiState.value = SearchUiState.Error("Error ${it.message}")
                }
        }
    }
}