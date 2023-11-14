package ar.com.jobsity.challenge.network.response

import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val genres: List<String>,
    val summary: String,
    val image: ShowImage,
    val schedule: ShowSchedule
)

@Serializable
data class ShowImage(val medium: String, val original: String)

@Serializable
data class ShowSchedule(val time: String, val days: List<String>)

@Serializable
data class SearchShow(val score: Long, val show: Show)
