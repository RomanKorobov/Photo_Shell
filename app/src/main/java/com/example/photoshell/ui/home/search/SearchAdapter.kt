package com.example.photoshell.ui.home.search

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoshell.R
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import com.example.photoshell.utils.inflate

class SearchAdapter(private val onItemClicked: (id: String) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.Holder>() {
    var searchList: List<UnsplashPhoto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_preview_photo), onItemClicked)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindViewHolder(searchList[position])
    }

    inner class Holder(view: View, onItemClicked: (id: String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val preview: ImageView = view.findViewById(R.id.previewPhotoImageview)
        private var theId: String? = null
        val id: String
            get() = theId!!

        fun bindViewHolder(photo: UnsplashPhoto) {
            Glide.with(itemView).load(photo.urls.regular).placeholder(R.drawable.ic_placeholder)
                .into(preview)
            theId = photo.id
        }

        init {
            view.setOnClickListener {
                onItemClicked(id)
            }
        }
    }
}