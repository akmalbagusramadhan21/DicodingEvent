package com.example.eventdicoding.ui

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.data.ListEventsItem
import com.example.eventdicoding.databinding.CardEventBinding
import com.example.eventdicoding.ui.detail.DetailEventActivity

class EventAdapter : ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        if (event != null) {
            holder.bind(event)
        }
    }

    class MyViewHolder(private val binding: CardEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvItemName.text = event.name ?: "Unknown ListEventResponse"
            binding.tvItemDescription.text =
                Html.fromHtml(event.description, Html.FROM_HTML_MODE_LEGACY)

            Glide.with(binding.imageView.context)
                .load(event.imageLogo)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra(DetailEventActivity.EXTRA_EVENT_ID, event.id)
                }
                context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
