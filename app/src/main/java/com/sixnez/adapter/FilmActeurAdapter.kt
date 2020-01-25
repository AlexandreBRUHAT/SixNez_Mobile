package com.sixnez.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sixnez.databinding.ItemFilmActeurViewBinding
import com.sixnez.model.FilmActeurDTO

class FilmActeurAdapter(val clickListener: FilmActeurListener) : ListAdapter<FilmActeurDTO, FilmActeurAdapter.ViewHolder>(FilmActeurDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemFilmActeurViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmActeurDTO, clickListener: FilmActeurListener) {
            binding.film = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFilmActeurViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class FilmActeurDiffCallback : DiffUtil.ItemCallback<FilmActeurDTO>() {
    override fun areItemsTheSame(oldItem: FilmActeurDTO, newItem: FilmActeurDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmActeurDTO, newItem: FilmActeurDTO): Boolean {
        return oldItem.equals(newItem)
    }
}


class FilmActeurListener(val clickListener: (FilmActeur: FilmActeurDTO) -> Unit) {
    fun onClick(FilmActeur: FilmActeurDTO) = clickListener(FilmActeur)
}