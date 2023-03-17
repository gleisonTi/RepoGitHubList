package com.example.repogithublist

import com.example.repogithublist.data.models.RepositoriesResponse
import com.example.repogithublist.data.repository.RepGitRepository
import com.example.repogithublist.ui.repositoryList.RepositoriesViewModel
import com.example.repogithublist.ui.repositoryList.RepositoryListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RepositoriesViewModelTest {

    @Mock
    private lateinit var repGitRepository: RepGitRepository

    private lateinit var repositoriesViewModel: RepositoriesViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoriesViewModel = RepositoriesViewModel(repGitRepository)
    }


    @Test
    fun ui() = runBlocking {

        val mockResponse = Response.success(200, mockRepositoriList())
        Mockito.`when`(repGitRepository.getAllRepositories(1)).thenReturn(mockResponse)
        // You need to launch here because submitData suspends forever while PagingData is alive
        val job = launch {
            repositoriesViewModel.repositoryListData.collectLatest {

                val result = it
            }
        }

        job.cancel()
    }


    fun mockRepositoriList ()  =
        RepositoriesResponse(
            incomplete_results = false,
            items = emptyList(),
            total_count = 10
        )

}


