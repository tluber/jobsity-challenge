package ar.com.jobsity.challenge.utils.spinner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.com.jobsity.challenge.R
import ar.com.jobsity.challenge.databinding.ViewSpinnerItemBinding

enum class SpinnerStyle {
    DEFAULT, TEXT
}

class SpinnerDialogAdapter<T>(
    private var list: List<Triple<String, Boolean, T>>,
    private var multipleSelection: Boolean = false,
    private val maxSelections: Int? = null,
    private var style: SpinnerStyle = SpinnerStyle.DEFAULT,
    private var listener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var states: MutableList<Boolean> = mutableListOf()
    var spinnerDataSource: SpinnerDataSource? = null

    val selectedItems: List<T>
        get() {

            val result = mutableListOf<T>()
            for ((i, state) in states.withIndex()) {
                if (state) {
                    result.add(list[i].third)
                }
            }
            return result
        }

    init {
        list.forEach {
            states.add(it.second)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return spinnerDataSource?.let {
            it.onCreateViewHolder(parent, viewType, onClickListener)
        } ?: run {
            //val view = LayoutInflater.from(parent.context).inflate(R.layout.view_spinner_item, parent, false)
            val binding =
                ViewSpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding, style, onClickListener)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list[position].let {
            when (holder) {
                is SpinnerItemViewHolder -> holder.bind(it.first, states[position], position)
                is ItemViewHolder -> holder.bind(it.first, states[position], position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.view_spinner_item
    }

    private val onClickListener = fun(position: Int) {
        val state = !states[position]
        if (!multipleSelection) {
            states.forEachIndexed { index, value ->
                if (value) {
                    states[index] = false
                    notifyItemChanged(index)
                }
            }
        } else {
            if (state && maxSelections != null && states.filter { it }.size > maxSelections - 1) {
                return
            }
        }

        states[position] = state
        notifyItemChanged(position)
        listener.invoke(position)
    }

    class ItemViewHolder(
        private val binding: ViewSpinnerItemBinding,
        private var style: SpinnerStyle = SpinnerStyle.DEFAULT,
        listener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = itemView.tag as Int
                listener.invoke(position)
            }
        }

        fun bind(it: String, state: Boolean, position: Int) {
            itemView.tag = position
            binding.spinnerItemTextView.text = it

            when (style) {
                SpinnerStyle.DEFAULT -> {
                    binding.spinnerItemCheckImage.visibility = when (state) {
                        false -> View.INVISIBLE
                        true -> View.VISIBLE
                    }
                }

                SpinnerStyle.TEXT -> {
                    binding.spinnerItemCheckImage.visibility = View.GONE
                    binding.spinnerItemTextView.setTextColor(
                        when (state) {
                            false -> ContextCompat.getColor(itemView.context, R.color.grey)
                            true -> ContextCompat.getColor(itemView.context, R.color.colorAccent)
                        }
                    )
                }
            }

        }
    }
}
