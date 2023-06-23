package com.example.photoshell.ui.albums

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.photoshell.R
import com.example.photoshell.databinding.FragmentAlbumsBinding
import com.example.photoshell.utils.autoCleared

class AlbumsFragment : Fragment(R.layout.fragment_albums) {
    private val binding: FragmentAlbumsBinding by viewBinding()
    private var fragmentAdapter: CollectionsAdapter by autoCleared()
    private val viewModel: AlbumsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(requireContext())
        bindViewModel()
    }

    private fun initList(context: Context) {
        fragmentAdapter = CollectionsAdapter(context) {
            val action = AlbumsFragmentDirections.actionAlbumsFragmentToCollectionPhotosFragment(it)
            findNavController().navigate(action)
        }
        with(binding.albumsRecView) {
            adapter = fragmentAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindViewModel() {
        viewModel.collections.observe(viewLifecycleOwner) {
            fragmentAdapter.collections = it
            fragmentAdapter.notifyDataSetChanged()
        }
        viewModel.getCollections()
    }
}