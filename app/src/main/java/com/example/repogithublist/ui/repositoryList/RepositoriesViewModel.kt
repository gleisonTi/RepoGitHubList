package com.example.repogithublist.ui.repositoryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.repogithublist.data.apis.ApiGitHubService
import com.example.repogithublist.data.repository.RepGitRepository
import com.example.repogithublist.paging.RepoGitHubPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repGitRepository: RepGitRepository
): ViewModel() {
    val repositoryListData = Pager(PagingConfig(pageSize = 1)) {
        RepoGitHubPagingSource(repGitRepository)
    }.flow.cachedIn(viewModelScope)

}