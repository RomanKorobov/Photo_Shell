package com.example.photoshell.ui.home.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.photoshell.R
import com.example.photoshell.databinding.FragmentSearchBinding
import com.example.photoshell.utils.autoCleared
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by viewBinding()
    private var fragmentAdapter: SearchAdapter by autoCleared()
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
        var query = ""
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                query = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        binding.searchViewEditText.addTextChangedListener(textWatcher)
        binding.startSearchButton.setOnClickListener {
            viewModel.searchPhotos(query)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initList() {
        fragmentAdapter = SearchAdapter { id ->
            lifecycleScope.launch {
                val photo = viewModel.getPhoto(id)
                val action = SearchFragmentDirections.actionSearchFragmentToHomePhotoFragment(
                    photo.id,
                    photo.urls.regular!!,
                    photo.user.username,
                    photo.links.download!!,
                    photo.links.html,
                    photo.likedByUser
                )
                findNavController().navigate(action)
            }
        }
        with(binding.searchRecView) {
            adapter = fragmentAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
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

    private fun bindViewModel() {
        viewModel.searchPhotos.observe(viewLifecycleOwner) {
            fragmentAdapter.searchList = it
            fragmentAdapter.notifyDataSetChanged()
        }
    }
}