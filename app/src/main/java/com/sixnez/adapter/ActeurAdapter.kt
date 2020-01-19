package com.sixnez.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sixnez.databinding.ItemActeurViewBinding
import com.sixnez.model.ActeurDTO

class ActeurAdapter(val clickListener: ActeurListener) : ListAdapter<ActeurDTO, ActeurAdapter.ViewHolder>(ActeurDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemActeurViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActeurDTO, clickListener: ActeurListener) {
            binding.acteur = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemActeurViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ActeurDiffCallback : DiffUtil.ItemCallback<ActeurDTO>() {
    override fun areItemsTheSame(oldItem: ActeurDTO, newItem: ActeurDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActeurDTO, newItem: ActeurDTO): Boolean {
        return oldItem.equals(newItem)
    }
}


class ActeurListener(val clickListener: (acteur: ActeurDTO) -> Unit) {
    fun onClick(acteur: ActeurDTO) = clickListener(acteur)
}