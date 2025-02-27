package com.ajeeb.spendie.main.data.di

import com.ajeeb.spendie.common.data.utils.ApiClient
import com.ajeeb.spendie.main.data.network.MainApiService
import com.ajeeb.spendie.main.data.repository.MainApiRepositoryImpl
import com.ajeeb.spendie.main.domain.repository.MainApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainApiRepositoryModule {

    @Provides
    @Singleton
    fun provideMainApiService(apiClient: ApiClient): MainApiService =
        Retrofit.Builder().baseUrl("https://api.vatcomply.com/").client(apiClient.instance)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideMainApiRepository(
        apiService: MainApiService
    ): MainApiRepository = MainApiRepositoryImpl(
        mainApiService = apiService
    )
}