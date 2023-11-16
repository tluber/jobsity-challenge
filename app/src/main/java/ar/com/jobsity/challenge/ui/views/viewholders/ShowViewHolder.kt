package ar.com.jobsity.challenge.ui.views.viewholders

import androidx.recyclerview.widget.RecyclerView
import ar.com.jobsity.challenge.databinding.ItemShowBinding
import ar.com.jobsity.challenge.network.response.Show

class ShowViewHolder(private val binding: ItemShowBinding, onItemClicked: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(show: Show) {

        itemView.tag = show
        binding.showImage.setImageURI(show.image.original)
        binding.showName.text = show.name
    }
}