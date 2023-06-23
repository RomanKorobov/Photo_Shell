package com.example.photoshell.ui.home.photo

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.photoshell.R
import com.example.photoshell.databinding.FragmentHomePhotoBinding

class HomePhotoFragment : Fragment(R.layout.fragment_home_photo) {
    private val binding: FragmentHomePhotoBinding by viewBinding()
    private val args: HomePhotoFragmentArgs by navArgs()
    private val viewModel: HomePhotoViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeIfLiked()
        Glide.with(this).load(args.photoUrl).transform(CenterCrop(), RoundedCorners(16))
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

            }).into(binding.imageHomePhotoFragment)
        binding.homePhotoUserTextView.text = args.user
        viewModel.setIfLiked(args.likedByUser)
        binding.backButtonHomePhoto.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.likeButtonHomePhoto.setOnClickListener {
            if (viewModel.likeLiveData.value == false) {
                viewModel.likePhoto(args.id)
            } else {
                viewModel.unlikePhoto(args.id)
            }
        }
        binding.shareButton.setOnClickListener {
            startActivity(viewModel.sharePhotoIntent(args.link))
        }
        binding.downloadButton.setOnClickListener {
            viewModel.downloadPhoto(args.downloadLink, args.id)
        }
    }

    private fun observeIfLiked() {
        viewModel.likeLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.likeButtonHomePhoto.setBackgroundResource(R.drawable.like_yellow_full)
            } else {
                binding.likeButtonHomePhoto.setBackgroundResource(R.drawable.like_yellow)
            }
        }
    }
}