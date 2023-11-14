package ar.com.jobsity.challenge.data.repository

import ar.com.jobsity.challenge.network.api.TvMazeApi
import ar.com.jobsity.challenge.network.response.Episode
import ar.com.jobsity.challenge.network.response.SearchShow
import ar.com.jobsity.challenge.network.response.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvMazeRepository @Inject constructor(
    private val tvMazeApi: TvMazeApi
) {

    suspend fun getShow(showId: Int): Result<Show> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val showResponse = tvMazeApi.getShow(showId)
                if (showResponse.isSuccessful) {
                    showResponse.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Network error ${showResponse.code()}"))
                } else {
                    Result.failure(Exception("Network error ${showResponse.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getShows(page: Int = 0): Result<List<Show>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val showResponse = tvMazeApi.getShows(page)
                if (showResponse.isSuccessful) {
                    showResponse.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Network error ${showResponse.code()}"))
                } else {
                    Result.failure(Exception("Network error ${showResponse.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getEpisodes(showId: Int): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val episodesResponse = tvMazeApi.getEpisodes(showId)
                if (episodesResponse.isSuccessful) {
                    episodesResponse.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Network error ${episodesResponse.code()}"))
                } else {
                    Result.failure(Exception("Network error ${episodesResponse.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun searchShows(query: String): Result<List<SearchShow>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val searchShowsResponse = tvMazeApi.searchShows(query)
                if (searchShowsResponse.isSuccessful) {
                    searchShowsResponse.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Network error ${searchShowsResponse.code()}"))
                } else {
                    Result.failure(Exception("Network error ${searchShowsResponse.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}