package com.example.repogithublist.ui.repositoryList

import com.example.repogithublist.paging.model.Repository

data class RepositoryListState(
    val isLoading: Boolean = false,
    val repositoryList: List<Repository> = emptyList(),
    val error: String = ""
)
