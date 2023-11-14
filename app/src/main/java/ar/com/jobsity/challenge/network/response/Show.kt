package ar.com.jobsity.challenge.network.response

data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val genres: List<String>,
    val summary: String,
    val image: ShowImage,
    val schedule: ShowSchedule
)

data class ShowImage(val medium: String, val original: String)

data class ShowSchedule(val time: String, val days: List<String>)

data class SearchShow(val score: Long, val show: Show)
