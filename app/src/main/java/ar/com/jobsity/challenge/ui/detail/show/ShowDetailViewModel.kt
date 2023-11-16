package ar.com.jobsity.challenge.ui.detail.show

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.jobsity.challenge.data.repository.TvMazeRepository
import ar.com.jobsity.challenge.network.response.Episode
import ar.com.jobsity.challenge.network.response.Season
import ar.com.jobsity.challenge.network.response.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

sealed interface EpisodesUiState {
    data object Loading : EpisodesUiState
    data class Success(
        val episodeList: List<Episode>
    ) : EpisodesUiState

    data class Error(val message: String) : EpisodesUiState
}

sealed interface SeasonsUiState {
    data object Loading : SeasonsUiState
    data class Success(
        val seasonList: List<Season>
    ) : SeasonsUiState

    data class Error(val message: String) : SeasonsUiState
}

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val tvMazeRepository: TvMazeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Show>("show")?.let {
            getSeasons(it.id)
        }
    }

    private val mEpisodesUiState: MutableStateFlow<EpisodesUiState> =
        MutableStateFlow(EpisodesUiState.Loading)
    val episodesUiState: StateFlow<EpisodesUiState> = mEpisodesUiState.asStateFlow()
    private val mSeasonsUiState: MutableStateFlow<SeasonsUiState> =
        MutableStateFlow(SeasonsUiState.Loading)
    val seasonsUiState: StateFlow<SeasonsUiState> = mSeasonsUiState.asStateFlow()

    fun getEpisodesFromSeason(seasonId: Int) {
        viewModelScope.launch {
            mEpisodesUiState.value = EpisodesUiState.Loading
            tvMazeRepository.getEpisodesFromSeason(seasonId)
                .onSuccess {
                    mEpisodesUiState.value = EpisodesUiState.Success(it)
                }.onFailure {
                    Timber.e(it)
                    mEpisodesUiState.value = EpisodesUiState.Error("Error ${it.message}")
                }
        }
    }

    private fun getSeasons(showId: Int) {
        viewModelScope.launch {
            mSeasonsUiState.value = SeasonsUiState.Loading
            tvMazeRepository.getSeasons(showId)
                .onSuccess { seasons ->
                    if (seasons.isNotEmpty()) {
                        getEpisodesFromSeason(seasons.first().id)
                    }
                    mSeasonsUiState.value = SeasonsUiState.Success(seasons)
                }.onFailure {
                    Timber.e(it)
                    mSeasonsUiState.value = SeasonsUiState.Error("Error ${it.message}")
                }
        }
    }

}