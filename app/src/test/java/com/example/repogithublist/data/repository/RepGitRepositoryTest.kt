package com.example.repogithublist.data.repository

import com.example.repogithublist.data.apis.ApiGitHubService
import com.example.repogithublist.data.models.RepositoriesResponse
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import retrofit2.HttpException
import retrofit2.Response

internal class RepGitRepositoryTest {

    private lateinit var repGitRepository: RepGitRepository

    @Mock
    private lateinit var apiGitHubService: ApiGitHubService


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repGitRepository = RepGitRepository(apiGitHubService)
    }

    @Test
    fun request_get_success_full_body_response() {

        val mockResponseBody = mock(RepositoriesResponse::class.java)
        val mockResponse = Response.success(200, mockResponseBody)
        runBlocking {
            `when`(
                apiGitHubService.getRepositories(1)
            ).thenReturn(mockResponse)

            val result =  repGitRepository.getAllRepositories(1)

            Assert.assertEquals(result.body()?.items?.size, 0)
        }
    }


}