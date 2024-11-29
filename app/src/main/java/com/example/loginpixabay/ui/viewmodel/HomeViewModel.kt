package com.example.loginpixabay.ui.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.load
import com.example.loginpixabay.data.model.ImageData
import com.example.loginpixabay.data.repository.PixabayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: PixabayRepository) : ViewModel() {

    var query = "flowers"

    private val _pagingData = MutableLiveData<PagingData<ImageData>>()
    val pagingData: LiveData<PagingData<ImageData>> = _pagingData

    val pager = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            object : PagingSource<Int, ImageData>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData> {
                    val page = params.key ?: 1
                    return try {
                        val pagingSource = repository.fetchImages(query, page)
                        pagingSource.load(params)
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }

                override fun getRefreshKey(state: PagingState<Int, ImageData>): Int? {
                    return state.anchorPosition
                }
            }
        }
    ).flow

    fun fetchImages() {
        viewModelScope.launch {
            pager.collectLatest { pagingData ->
                _pagingData.value = pagingData
            }
        }
    }

    private val _navigateToDetail = MutableLiveData<ImageData?>()
    val navigateToDetail: LiveData<ImageData?> = _navigateToDetail

    private val _imageDetail = MutableLiveData<ImageData>()
    val imageDetail: LiveData<ImageData> = _imageDetail

    fun setImageDetail(imageItem: ImageData) {
        _imageDetail.value = imageItem
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    object BindingAdapters {

        @BindingAdapter("image_url")
        @JvmStatic
        fun ImageView.loadImage(imageURL: String? = "") {
            this.load(imageURL)
        }
    }
}
