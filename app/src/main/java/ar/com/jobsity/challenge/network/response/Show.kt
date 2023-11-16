package ar.com.jobsity.challenge.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("summary") val summary: String,
    @SerializedName("image") val image: ShowImage?,
    @SerializedName("schedule") val schedule: ShowSchedule
) : Parcelable

@Parcelize
data class ShowImage(
    @SerializedName("medium") val medium: String,
    @SerializedName("original") val original: String
) : Parcelable

@Parcelize
data class ShowSchedule(
    @SerializedName("time") val time: String,
    @SerializedName("days") val days: List<String>
) : Parcelable

@Parcelize
data class SearchShow(
    @SerializedName("score") val score: Float,
    @SerializedName("show") val show: Show
) : Parcelable
