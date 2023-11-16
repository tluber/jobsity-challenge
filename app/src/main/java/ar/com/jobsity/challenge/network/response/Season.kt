package ar.com.jobsity.challenge.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("number") val number: Int
) : Parcelable

fun Season.toTriple(): Triple<String, Boolean, Season> {
    return Triple("Season ${this.number}", false, this)
}