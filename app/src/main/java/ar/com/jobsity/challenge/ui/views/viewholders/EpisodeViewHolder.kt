package ar.com.jobsity.challenge.ui.views.viewholders

import androidx.recyclerview.widget.RecyclerView
import ar.com.jobsity.challenge.databinding.ItemEpisodeBinding
import ar.com.jobsity.challenge.network.response.Episode

class EpisodeViewHolder(private val binding: ItemEpisodeBinding, onItemClicked: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(episode: Episode) {

        itemView.tag = episode
        binding.episodeImage.setImageURI(episode.image?.original)
        binding.episodeName.text = "${episode.number}. ${episode.name}"
    }
}