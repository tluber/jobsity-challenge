package ar.com.jobsity.challenge.data.repository

import ar.com.jobsity.challenge.network.api.TvMazeApi
import ar.com.jobsity.challenge.network.response.Episode
import ar.com.jobsity.challenge.network.response.SearchShow
import ar.com.jobsity.challenge.network.response.Season
import ar.com.jobsity.challenge.network.response.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvMazeRepository @Inject constructor(
    private val tvMazeApi: TvMazeApi
) {

    /**
     * Fetches a specific TV show from the TV Maze API based on the provided show ID.
     *
     * @param showId The unique identifier of the TV show to retrieve.
     * @return A [Result] object containing either a [Show] on success or an [Exception] on failure.
     * @throws Exception if there is a network error or an exception during the API call.
     *
     * This function is designed to be called from a coroutine. It suspends the current coroutine
     * and performs the API call on the IO dispatcher using [withContext].
     */
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

    /**
     * Fetches a list of TV shows from the TV Maze API.
     *
     * @param page The page number of the shows to retrieve. Default is 0.
     * @return A [Result] object containing either a [List] of [Show] on success or an [Exception] on failure.
     * @throws Exception if there is a network error or an exception during the API call.
     *
     * This function is designed to be called from a coroutine. It suspends the current coroutine
     * and performs the API call on the IO dispatcher using [withContext].
     */
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

    /**
     * Fetches a list of episodes for a specific TV show from the TV Maze API based on the provided show ID.
     *
     * @param showId The unique identifier of the TV show for which to retrieve episodes.
     * @return A [Result] object containing either a [List] of [Episode] on success or an [Exception] on failure.
     * @throws Exception if there is a network error or an exception during the API call.
     *
     * This function is designed to be called from a coroutine. It suspends the current coroutine
     * and performs the API call on the IO dispatcher using [withContext].
     */
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

    /**
     * Performs a search for TV shows using the TV Maze API based on the provided query.
     *
     * @param query The search query to find matching TV shows.
     * @return A [Result] object containing either a [List] of [SearchShow] on success or an [Exception] on failure.
     * @throws Exception if there is a network error or an exception during the API call.
     *
     * This function is designed to be called from a coroutine. It suspends the current coroutine
     * and performs the API call on the IO dispatcher using [withContext].
     */
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

    /**
     * Fetches a list of seasons for a specific TV show from the TV Maze API based on the provided show ID.
     *
     * @param showId The unique identifier of the TV show for which to retrieve seasons.
     * @return A [Result] object containing either a [List] of [Season] on success or an [Exception] on failure.
     * @throws Exception if there is a network error or an exception during the API call.
     *
     * This function is designed to be called from a coroutine. It suspends the current coroutine
     * and performs the API call on the IO dispatcher using [withContext].
     */
    suspend fun getSeasons(showId: Int): Result<List<Season>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val seasonsResponse = tvMazeApi.getSeasons(showId)
                if (seasonsResponse.isSuccessful) {
                    seasonsResponse.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Network error ${seasonsResponse.code()}"))
                } else {
                    Result.failure(Exception("Network error ${seasonsResponse.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /**
     * Fetches a list of episodes for a specific season from the TV Maze API based on the provided season ID.
     *
     * @param seasonId The unique identifier of the season for which to retrieve episodes.
     * @return A [Result] object containing either a [List] of [Episode] on success or an [Exception] on failure.
     * @throws Exception if there is a network error or an exception during the API call.
     *
     * This function is designed to be called from a coroutine. It suspends the current coroutine
     * and performs the API call on the IO dispatcher using [withContext].
     */
    suspend fun getEpisodesFromSeason(seasonId: Int): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val seasonEpisodesResponse = tvMazeApi.getEpisodesFromSeason(seasonId)
                if (seasonEpisodesResponse.isSuccessful) {
                    seasonEpisodesResponse.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Network error ${seasonEpisodesResponse.code()}"))
                } else {
                    Result.failure(Exception("Network error ${seasonEpisodesResponse.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}