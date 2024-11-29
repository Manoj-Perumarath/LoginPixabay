package com.example.loginpixabay.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PixabayResponse(
    val hits: List<ImageData>
)

@Parcelize
data class ImageData(
    val id: Int,
    val user: String,
    val tags: String,
    val type: String,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val favorites: Int,
    val downloads: Int,
    val previewURL: String?="",
    val largeImageURL: String,
    val imageSize: Int
) : Parcelable
