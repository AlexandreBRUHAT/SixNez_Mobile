package com.sixnez.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sixnez.databinding.ItemActeurFilmViewBinding
import com.sixnez.model.ActeurFilmDTO

class ActeurFilmAdapter(val clickListener: ActeurFilmListener) : ListAdapter<ActeurFilmDTO, ActeurFilmAdapter.ViewHolder>(ActeurFilmDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemActeurFilmViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActeurFilmDTO, clickListener: ActeurFilmListener) {
            binding.acteur = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemActeurFilmViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ActeurFilmDiffCallback : DiffUtil.ItemCallback<ActeurFilmDTO>() {
    override fun areItemsTheSame(oldItem: ActeurFilmDTO, newItem: ActeurFilmDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActeurFilmDTO, newItem: ActeurFilmDTO): Boolean {
        return oldItem.equals(newItem)
    }
}


class ActeurFilmListener(val clickListener: (ActeurFilm: ActeurFilmDTO) -> Unit) {
    fun onClick(ActeurFilm: ActeurFilmDTO) = clickListener(ActeurFilm)
}