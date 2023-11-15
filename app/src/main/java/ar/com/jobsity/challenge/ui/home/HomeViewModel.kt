package ar.com.jobsity.challenge.ui.home

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

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val showList: List<Show>
    ) : HomeUiState

    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tvMazeRepository: TvMazeRepository
) : ViewModel() {

    init {
        getShows()
    }

    private val mHomeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = mHomeUiState.asStateFlow()

    fun getShows(page: Int = 0) {
        viewModelScope.launch {
            mHomeUiState.value = HomeUiState.Loading
            tvMazeRepository.getShows(page)
                .onSuccess {
                    mHomeUiState.value = HomeUiState.Success(it)
                }.onFailure {
                    Timber.e(it)
                    mHomeUiState.value = HomeUiState.Error("Error ${it.message}")
                }
        }
    }

}