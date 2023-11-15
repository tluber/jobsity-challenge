package ar.com.jobsity.challenge.network.response

import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("summary") val summary: String,
    @SerializedName("image") val image: ShowImage,
    @SerializedName("schedule") val schedule: ShowSchedule
)

data class ShowImage(
    @SerializedName("medium") val medium: String,
    @SerializedName("original") val original: String
)

data class ShowSchedule(
    @SerializedName("time") val time: String,
    @SerializedName("days") val days: List<String>
)

data class SearchShow(
    @SerializedName("score") val score: Long,
    @SerializedName("show") val show: Show
)
