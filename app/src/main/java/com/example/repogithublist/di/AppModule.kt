package com.example.repogithublist.di

import com.example.repogithublist.data.apis.ApiGitHubService
import com.example.repogithublist.data.helper.Constants
import com.example.repogithublist.data.repository.RepGitRepository
import com.example.repogithublist.domain.GetRepositoriesUseCase
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
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): ApiGitHubService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiGitHubService::class.java)

    @Provides
    @Singleton
    fun provideRepGitRepositoy( apiGitHubService: ApiGitHubService) : RepGitRepository =
        RepGitRepository(apiGitHubService)

    @Provides
    @Singleton
    fun provideGetRepositoriesUseCase(repGitRepository: RepGitRepository) : GetRepositoriesUseCase =
        GetRepositoriesUseCase(repGitRepository)

}