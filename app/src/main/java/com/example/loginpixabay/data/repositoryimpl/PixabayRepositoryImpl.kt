package com.example.loginpixabay.data.repositoryimpl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.loginpixabay.data.api.ApiService
import com.example.loginpixabay.data.constants.AppConstants
import com.example.loginpixabay.data.model.ImageData
import com.example.loginpixabay.data.model.PixabayResponse
import com.example.loginpixabay.data.model.ResourceState
import com.example.loginpixabay.data.repository.PixabayRepository
import javax.inject.Inject

class PixabayRepositoryImpl @Inject constructor(val apiService: ApiService) : PixabayRepository {
    override suspend fun fetchImages(query: String): ResourceState<PixabayResponse> {
        return try {
            val response = apiService.getImages(AppConstants.PIX_API_KEY, query, "photo", 1)
            if (response.isSuccessful && response.body() != null) {
                ResourceState.Success(response.body()!!)
            } else {
                ResourceState.Failure(response.errorBody()?.string() ?: "Unknown Error")
            }
        } catch (e: Exception) {
            ResourceState.Failure(e.message ?: "Network Error")
        }
    }

    override suspend fun fetchImages(query: String, page: Int): PagingSource<Int, ImageData> {

        return object : PagingSource<Int, ImageData>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData> {
                val page = params.key ?: 1
                return try {
                    val response =
                        apiService.getImages(AppConstants.PIX_API_KEY, query, "photo", page)
                    if (response.isSuccessful && response.body() != null) {
                        val images = response.body()!!.hits
                        LoadResult.Page(
                            data = images,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (images.isEmpty()) null else page + 1
                        )
                    } else {
                        LoadResult.Error(Exception("Error loading images"))
                    }
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, ImageData>): Int? {
                return state.anchorPosition
            }
        }
    }
}