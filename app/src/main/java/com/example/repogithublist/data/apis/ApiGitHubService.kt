package com.example.repogithublist.data.apis

import com.example.repogithublist.data.helper.Constants
import com.example.repogithublist.data.models.RepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiGitHubService {
    @GET(Constants.END_POINT)
    suspend fun getRepositories(): Response<RepositoriesResponse>
}