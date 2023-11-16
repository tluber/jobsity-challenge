package ar.com.jobsity.challenge.utils.extensions

fun String.removeHtmlTags(): String {
    return this.replace("<[^>]*>".toRegex(), "")
}
