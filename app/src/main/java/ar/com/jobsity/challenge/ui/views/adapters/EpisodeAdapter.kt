package ar.com.jobsity.challenge.ui.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.com.jobsity.challenge.databinding.ItemEpisodeBinding
import ar.com.jobsity.challenge.network.response.Episode
import ar.com.jobsity.challenge.ui.views.viewholders.EpisodeViewHolder

class EpisodeAdapter(private val onItemClick: (Episode) -> Unit) :
    RecyclerView.Adapter<EpisodeViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Episode>() {

        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            ItemEpisodeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return EpisodeViewHolder(binding) {
            onItemClick(differ.currentList[it])
        }
    }

    override fun onBindViewHolder(viewHolder: EpisodeViewHolder, position: Int) {
        viewHolder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun refresh(list: List<Episode>) {
        differ.submitList(list)
    }
}
