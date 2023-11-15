package ar.com.jobsity.challenge.network.response

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("season") val season: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("summary") val summary: String,
    @SerializedName("image") val image: EpisodeImage?
)

data class EpisodeImage(
    @SerializedName("medium") val medium: String,
    @SerializedName("original") val original: String
)