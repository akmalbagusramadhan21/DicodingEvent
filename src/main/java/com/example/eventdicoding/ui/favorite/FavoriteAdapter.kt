package com.example.eventdicoding.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.databinding.CardEventBinding
import com.example.eventdicoding.ui.database.FavoriteEvent
import com.example.eventdicoding.ui.detail.DetailEventActivity

class FavoriteAdapter :
    ListAdapter<FavoriteEvent, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoriteViewHolder(private val binding: CardEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEvent: FavoriteEvent) {
            binding.tvItemName.text = favoriteEvent.name
            Glide.with(binding.root.context)
                .load(favoriteEvent.mediaCover)
                .into(binding.imageView)
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.EXTRA_EVENT_ID, favoriteEvent.id)
                binding.root.context.startActivity(intent)

            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEvent> =
            object : DiffUtil.ItemCallback<FavoriteEvent>() {
                override fun areItemsTheSame(
                    oldUser: FavoriteEvent,
                    newUser: FavoriteEvent
                ): Boolean {
                    return oldUser.name == newUser.name
                }

                override fun areContentsTheSame(
                    oldUser: FavoriteEvent,
                    newUser: FavoriteEvent
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }
}