package com.example.repogithublist.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.repogithublist.data.apis.ApiGitHubService
import com.example.repogithublist.data.models.RepositoriesResponse
import com.example.repogithublist.data.repository.RepGitRepository
import com.example.repogithublist.paging.model.Repository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RepoGitHubPagingSourceTest {


    private lateinit var repoGitHubPagingSource: RepoGitHubPagingSource

    @Mock
    private lateinit var repGitRepository: RepGitRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repoGitHubPagingSource = RepoGitHubPagingSource(repGitRepository)
    }

    @Test
    fun `reviews paging source load - failure - received null`() = runBlocking {
        given(repGitRepository.getAllRepositories(1)).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Repository>(NullPointerException())
        assertEquals(
            expectedResult.toString(), repoGitHubPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `reviews paging source refresh - success`() = runBlocking {

        val mockResponse = Response.success(200, mockRepositoriList())
        `when`(repGitRepository.getAllRepositories(1)).thenReturn(mockResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = mockRepositoriList().items,
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, repoGitHubPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `call getRefreshKey`() = runBlocking {

        val state =  PagingState<Int, Repository>(
            pages = listOf(),
            anchorPosition = 1,
            config = PagingConfig(pageSize = 1),
            leadingPlaceholderCount = 1
        )

        repoGitHubPagingSource.getRefreshKey(state)

        assertNull(repoGitHubPagingSource.getRefreshKey(state))
    }

    fun mockRepositoriList ()  =
        RepositoriesResponse(
            incomplete_results = false,
            items = emptyList(),
            total_count = 10
    )

}





