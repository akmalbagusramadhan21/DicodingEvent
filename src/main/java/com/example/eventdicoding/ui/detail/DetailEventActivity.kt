package com.example.eventdicoding.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.eventdicoding.R

import com.example.eventdicoding.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val viewModel: DetailEventViewModel by viewModels()

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)

        if (eventId != -1) {
            viewModel.fetchEventDetail(eventId)

        }

        viewModel.eventDetail.observe(this) { detailEvent ->
            if (detailEvent != null) {
                Glide.with(this).load(detailEvent.mediaCover).into(binding.imageViewDetail)
                binding.tvDetailName.text = detailEvent.name
                binding.tvOwnerName.text = "Diselanggarakan Oleh ${detailEvent.ownerName}"
                binding.tvBeginTime.text = "Tanggal Mulai ${detailEvent.beginTime}"
                binding.tvSisaKuota.text =
                    "Sisa Kuota ${detailEvent.quota!! - detailEvent.registrants!!} Peserta"
                binding.tvDetailDescription.text =
                    Html.fromHtml(detailEvent.description, Html.FROM_HTML_MODE_COMPACT)

                binding.btnOpenLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailEvent.link))
                    startActivity(intent)
                }

                binding.btnFavorite.setOnClickListener {
                    viewModel.toggleFavorite(detailEvent)
                }
            }
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_kosong)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}