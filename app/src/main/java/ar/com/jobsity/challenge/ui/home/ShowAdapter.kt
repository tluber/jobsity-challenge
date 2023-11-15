package ar.com.jobsity.challenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.com.jobsity.challenge.databinding.ItemShowBinding
import ar.com.jobsity.challenge.network.response.Show

class ShowAdapter : RecyclerView.Adapter<ShowViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Show>() {

        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShowViewHolder {
        val binding =
            ItemShowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ShowViewHolder, position: Int) {
        viewHolder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun refresh(list: List<Show>) {
        differ.submitList(list)
    }
}
