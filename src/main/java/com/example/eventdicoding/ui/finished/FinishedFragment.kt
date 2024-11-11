package com.example.eventdicoding.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentFinishedBinding
import com.example.eventdicoding.ui.EventAdapter
import com.example.eventdicoding.ui.FinishedViewModel

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private lateinit var finishedViewModel: FinishedViewModel
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        finishedViewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root


        eventAdapter = EventAdapter()
        binding.recyclerViewFinished.adapter = eventAdapter
        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(requireContext())


        finishedViewModel.event.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        finishedViewModel.fetchApi()

        return binding.root
    }

}

