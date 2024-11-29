package com.example.loginpixabay.data.repository

import androidx.paging.PagingSource
import com.example.loginpixabay.data.model.ImageData
import com.example.loginpixabay.data.model.PixabayResponse
import com.example.loginpixabay.data.model.ResourceState

interface PixabayRepository {
    suspend fun fetchImages(query: String, page: Int): PagingSource<Int, ImageData>

    suspend fun fetchImages(query: String): ResourceState< PixabayResponse>

}