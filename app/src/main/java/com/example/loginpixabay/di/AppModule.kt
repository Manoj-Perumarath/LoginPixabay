package com.example.loginpixabay.di

import com.example.loginpixabay.data.api.ApiService
import com.example.loginpixabay.data.constants.AppConstants
import com.example.loginpixabay.data.repository.PixabayRepository
import com.example.loginpixabay.data.repository.UserRepository
import com.example.loginpixabay.data.repositoryimpl.PixabayRepositoryImpl
import com.example.loginpixabay.data.repositoryimpl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitInstance().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun RetrofitInstance() = Retrofit.Builder()
        .baseUrl(AppConstants.PIX_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesUserRepoImpl(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesPixabayRepository(apiService: ApiService): PixabayRepository {
        return PixabayRepositoryImpl(apiService)
    }


}