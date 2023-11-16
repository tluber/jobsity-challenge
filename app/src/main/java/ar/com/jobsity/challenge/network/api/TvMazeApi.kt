package ar.com.jobsity.challenge.network.api

import ar.com.jobsity.challenge.network.response.Episode
import ar.com.jobsity.challenge.network.response.SearchShow
import ar.com.jobsity.challenge.network.response.Season
import ar.com.jobsity.challenge.network.response.Show
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApi {

    @GET("/shows")
    suspend fun getShows(@Query("page") page: Int): Response<List<Show>>

    @GET("/search/shows")
    suspend fun searchShows(@Query("q") query: String): Response<List<SearchShow>>

    @GET("shows/{showId}")
    suspend fun getShow(@Path("showId") showId: Int): Response<Show>

    @GET("shows/{showId}/episodes")
    suspend fun getEpisodes(@Path("showId") showId: Int): Response<List<Episode>>

    @GET("shows/{showId}/seasons")
    suspend fun getSeasons(@Path("showId") showId: Int): Response<List<Season>>

    @GET("seasons/{seasonId}/episodes")
    suspend fun getEpisodesFromSeason(@Path("seasonId") seasonId: Int): Response<List<Episode>>
}