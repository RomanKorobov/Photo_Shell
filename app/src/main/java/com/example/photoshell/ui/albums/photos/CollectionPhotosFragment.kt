package com.example.photoshell.ui.albums.photos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.photoshell.R
import com.example.photoshell.databinding.FragmentCollectionPhotosBinding
import com.example.photoshell.utils.autoCleared
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionPhotosFragment : Fragment(R.layout.fragment_collection_photos) {
    private val binding: FragmentCollectionPhotosBinding by viewBinding()
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private var fragmentAdapter: CollectionPhotosAdapter by autoCleared()
    private val viewModel: CollectionPhotosViewModel by viewModels()
    private val args: CollectionPhotosFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCollectionTitle()
        initList(requireContext())
        bindViewModel()
        binding.backToAlbumsButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initList(context: Context) {
        fragmentAdapter = CollectionPhotosAdapter { id ->
            viewModel.openPhotoById(id) { photo ->
                val action =
                    CollectionPhotosFragmentDirections.actionCollectionPhotosFragmentToHomePhotoFragment(
                        photo.id,
                        photo.urls.full!!,
                        photo.user.id,
                        photo.links.download!!,
                        photo.links.html,
                        photo.likedByUser
                    )
                findNavController().navigate(action)
            }
        }
        with(binding.collectionPhotosRecView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = fragmentAdapter
            this.setOnTouchListener { _, event ->
                val lastVisibleItemPosition =
                    (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val itemCount = (layoutManager as GridLayoutManager).itemCount
                if (lastVisibleItemPosition == itemCount - 1) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        viewModel.addPhotos(args.collectionId)
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

    private fun bindViewModel() {
        viewModel.photosInCollection.observe(viewLifecycleOwner) {
            fragmentAdapter.list = it
            fragmentAdapter.notifyDataSetChanged()
        }
        viewModel.getPhotos(args.collectionId)
    }

    private fun setCollectionTitle() {
        lifecycleScope.launch(Dispatchers.Main + handler) {
            binding.collectionNameTextView.text = viewModel.getCollectionName(args.collectionId)
        }
    }
}