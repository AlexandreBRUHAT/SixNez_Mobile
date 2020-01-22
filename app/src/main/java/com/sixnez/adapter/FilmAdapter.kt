package com.sixnez.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sixnez.databinding.ItemFilmViewBinding
import com.sixnez.model.FilmDTO

class FilmAdapter(val clickListener: FilmListener) : ListAdapter<FilmDTO, FilmAdapter.ViewHolder>(FilmDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemFilmViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmDTO, clickListener: FilmListener) {
            binding.film = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFilmViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class FilmDiffCallback : DiffUtil.ItemCallback<FilmDTO>() {
    override fun areItemsTheSame(oldItem: FilmDTO, newItem: FilmDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmDTO, newItem: FilmDTO): Boolean {
        return oldItem.equals(newItem)
    }
}


class FilmListener(val clickListener: (Film: FilmDTO) -> Unit) {
    fun onClick(Film: FilmDTO) = clickListener(Film)
}