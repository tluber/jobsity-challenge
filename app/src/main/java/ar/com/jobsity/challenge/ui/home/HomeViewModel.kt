package ar.com.jobsity.challenge.ui.home

import androidx.lifecycle.ViewModel
import ar.com.jobsity.challenge.data.repository.TvMazeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tvMazeRepository: TvMazeRepository
) : ViewModel() {

}