package com.example.photoshell.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.photoshell.MainActivity
import com.example.photoshell.R
import com.example.photoshell.databinding.FragmentProfileBinding
import com.example.photoshell.ui.home.PreviewPhotoAdapter
import com.example.photoshell.utils.autoCleared
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding: FragmentProfileBinding by viewBinding()
    private var fragmentAdapter: PreviewPhotoAdapter by autoCleared()
    private val viewModel: ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLikedList()
        bindViewModel(requireContext())
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLikedList() {
        fragmentAdapter = PreviewPhotoAdapter { id ->
            lifecycleScope.launch {
                val likedPhoto = viewModel.openPhotoById(id)
                val action = ProfileFragmentDirections.actionProfileFragmentToHomePhotoFragment(
                    likedPhoto.id,
                    likedPhoto.urls.full!!,
                    likedPhoto.user.id,
                    likedPhoto.links.download!!,
                    likedPhoto.links.html,
                    likedPhoto.likedByUser
                )
                findNavController().navigate(action)
            }
        }
        with(binding.profileRecView) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = fragmentAdapter
            this.setOnTouchListener { _, event ->
                val lastVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val itemCount = (layoutManager as LinearLayoutManager).itemCount
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

    private fun bindViewModel(context: Context) {
        lifecycleScope.launch {
            viewModel.profileFlow.collect {
                binding.textViewProfileFirstName.text = it.firstName
                binding.textViewProfileLastName.text = it.lastName
                binding.textViewProfileBio.text = getString(R.string.likes)
            }
        }
        lifecycleScope.launch {
            viewModel.likedPhotosFlow.collect {
                fragmentAdapter.list = it
                fragmentAdapter.notifyDataSetChanged()
                if (fragmentAdapter.list.isEmpty()) {
                    binding.textViewProfileBio.text = viewModel.profileFlow.value?.bio
                } else {
                    binding.textViewProfileBio.text = getString(R.string.likes)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.avatarImageFlow.collect {
                Glide.with(context).load(it).placeholder(R.drawable.avatar_placeholder)
                    .into(binding.imageViewAvatar)
            }
        }
        viewModel.getMyProfile()
    }
}