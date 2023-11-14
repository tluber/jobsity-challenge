package ar.com.jobsity.challenge.network.response

import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val genres: List<String>,
    val summary: String,
    val image: EpisodeImage?
)

@Serializable
data class EpisodeImage(val medium: String, val original: String)