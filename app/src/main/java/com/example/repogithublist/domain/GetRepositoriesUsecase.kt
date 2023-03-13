package com.example.repogithublist.domain

import com.example.repogithublist.data.helper.Response
import com.example.repogithublist.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface GetRepositoriesUsecaseInt {
    suspend fun invoke(): Flow<Response<List<Repository>>>
}