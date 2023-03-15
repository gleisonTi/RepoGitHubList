package com.example.repogithublist.data.repository

import com.example.repogithublist.data.models.RepositoriesResponse
import retrofit2.Response

interface RepoGitRepositoryInt {
    suspend fun getAllRepositories(page: Int): Response<RepositoriesResponse>
}