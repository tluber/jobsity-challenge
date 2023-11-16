package ar.com.jobsity.challenge.utils.spinner

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SpinnerDataSource {
    abstract fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
        listener: (Int) -> Unit
    ): SpinnerItemViewHolder
}

abstract class SpinnerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(it: String, state: Boolean, position: Int)
}
