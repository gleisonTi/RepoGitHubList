package com.example.repogithublist.data.apis

import com.example.repogithublist.data.helper.Constants
import com.example.repogithublist.data.models.RepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGitHubService {
    @GET(Constants.END_POINT)
    suspend fun getRepositories(
        @Query("page") page: Int,
    ): Response<RepositoriesResponse>
}