package com.example.loginpixabay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.loginpixabay.data.model.ImageData
import com.example.loginpixabay.databinding.ItemPixabayBinding

class PixabayAdapter(private val onClick: (ImageData) -> Unit) :
    PagingDataAdapter<ImageData, PixabayAdapter.ImageViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPixabayBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class ImageViewHolder(private val binding: ItemPixabayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageData) {
            binding.thumbnail.load(item.previewURL)
            binding.userName.text = item.user
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData) = oldItem == newItem
    }
}
