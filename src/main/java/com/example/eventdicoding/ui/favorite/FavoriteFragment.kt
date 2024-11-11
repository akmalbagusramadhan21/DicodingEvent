package com.example.eventdicoding.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter()
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorite.adapter = adapter

        viewModel.loadFavorite().observe(viewLifecycleOwner, Observer { favoriteEvents ->
            adapter.submitList(favoriteEvents)
        })

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.loadFavoriteEvents()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}