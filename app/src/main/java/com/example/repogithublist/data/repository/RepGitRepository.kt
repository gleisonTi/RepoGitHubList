package com.example.repogithublist.data.repository

import com.example.repogithublist.data.apis.ApiGitHubService
import javax.inject.Inject

class RepGitRepository @Inject
constructor(
    private val apiGitHubService: ApiGitHubService
): RepoGitRepositoryInt
{
    override suspend fun getAllRepositories(page: Int) = apiGitHubService.getRepositories(page = page)
}


