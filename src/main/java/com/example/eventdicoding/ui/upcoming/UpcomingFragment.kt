package com.example.eventdicoding.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentUpcomingBinding
import com.example.eventdicoding.ui.EventAdapter
import com.example.eventdicoding.ui.UpcomingViewModel

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        eventAdapter = EventAdapter()
        binding.recyclerViewUpcoming.adapter = eventAdapter
        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(requireContext())


        upcomingViewModel.event.observe(viewLifecycleOwner) { events ->
            val limitedEventList = events
            Log.d("UpcomingFragment", "Jumlah event yang diambil: ${limitedEventList.size}")
            eventAdapter.submitList(limitedEventList)
        }


        upcomingViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.recyclerViewUpcoming.setItemViewCacheSize(0)


        upcomingViewModel.fetchApi()

        return binding.root
    }


}
