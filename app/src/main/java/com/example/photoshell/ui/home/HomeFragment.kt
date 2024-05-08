package com.example.photoshell.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.photoshell.R
import com.example.photoshell.data.KeyStoreManager
import com.example.photoshell.databinding.FragmentHomeBinding
import com.example.photoshell.utils.autoCleared
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var fragmentAdapter: PreviewPhotoAdapter by autoCleared()
    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        bindViewModel()
        binding.searchButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAdapter() {
        fragmentAdapter = PreviewPhotoAdapter { id ->
            viewModel.openPhotoById(id) { photo ->
                val action = HomeFragmentDirections.actionHomeFragmentToHomePhotoFragment(
                    photo.id,
                    photo.urls.full!!,
                    photo.user.username,
                    photo.links.download!!,
                    photo.links.html,
                    photo.likedByUser
                )
                findNavController().navigate(action)
            }
        }
        with(binding.homeRecView) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = fragmentAdapter
            this.setOnTouchListener { _, event ->
                val lastVisibleItemPosition =
                    (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val itemCount = (layoutManager as GridLayoutManager).itemCount
                if (lastVisibleItemPosition == itemCount - 1) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        viewModel.addPhotos()
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
        }
    }

    private fun observeHomePhotos() {
        viewModel.photos.observe(viewLifecycleOwner) {
            fragmentAdapter.list = it
            fragmentAdapter.notifyDataSetChanged()
        }
    }

    private fun bindViewModel() {
        observeHomePhotos()
        lifecycleScope.launch {
            KeyStoreManager.tokenFlow.collect {
                viewModel.updateAccessToken()
                viewModel.getPhotos()
            }
        }
    }
}