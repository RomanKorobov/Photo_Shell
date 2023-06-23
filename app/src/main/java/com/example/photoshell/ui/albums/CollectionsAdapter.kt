package com.example.photoshell.ui.albums

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.photoshell.R
import com.example.photoshell.data.retrofitclasses.Collection
import com.example.photoshell.utils.inflate

class CollectionsAdapter(
    private val context: Context,
    private val onItemClicked: (id: String) -> Unit
) : RecyclerView.Adapter<CollectionsAdapter.Holder>() {
    var collections = listOf<Collection>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_collection), onItemClicked)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindViewHolder(collections[position])
    }

    inner class Holder(view: View, onItemClicked: (id: String) -> Unit) : ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.textViewCollectionTitle)
        private val numberOfPhotos: TextView = view.findViewById(R.id.textViewCollectionTotalPhotos)
        private val collectionPhoto: ImageView = view.findViewById(R.id.imageViewCollection)
        private var collectionId: String? = null
        fun bindViewHolder(collection: Collection) {
            title.text = collection.title
            numberOfPhotos.text =
                context.getString(R.string.photos_in_collection, collection.totalPhotos)
            Glide.with(itemView).load(collection.coverPhoto.urls.regular)
                .placeholder(R.drawable.album_placeholder).into(collectionPhoto)
            collectionId = collection.id
        }

        init {
            view.setOnClickListener {
                if (collectionId != null) {
                    onItemClicked(collectionId!!)
                }
            }
        }
    }
}