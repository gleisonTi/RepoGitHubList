package com.example.repogithublist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.repogithublist.data.apis.ApiGitHubService
import com.example.repogithublist.paging.model.Repository

class RepoGitHubPagingSource (
    private val apiGitHubService: ApiGitHubService
) : PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiGitHubService.getRepositories(currentPage)
            val responseData = mutableListOf<Repository>()
            val data = response.body()?.items?.map{
                Repository(
                    name = it.full_name,
                    author = it.owner.login,
                    image = it.owner.avatar_url,
                    stargazersCount = it.stargazers_count,
                    forksCount = it.forks_count
                )
            }
            responseData.addAll(data as Collection<Repository>)
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}