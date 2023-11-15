package ar.com.jobsity.challenge.utils.extensions

import android.content.res.Resources

// Px to Dp
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

// Dp to Px
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
