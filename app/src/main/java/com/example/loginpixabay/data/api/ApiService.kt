package com.example.loginpixabay.data.api

import com.example.loginpixabay.data.constants.AppConstants
import com.example.loginpixabay.data.model.PixabayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(AppConstants.PIX_API_PHOTO)
    suspend fun getImages(
        @Query("key") apiKey: String?,
        @Query("q") query: String?,
        @Query("image_type") imageType: String?,
        @Query("page") page: Int? = 1,
    ): Response<PixabayResponse>
}