package com.example.repogithublist.domain

import com.example.repogithublist.data.helper.Response
import com.example.repogithublist.data.repository.RepGitRepository
import com.example.repogithublist.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(private val repGitRepository: RepGitRepository ) : GetRepositoriesUsecaseInt
{
    override suspend fun invoke(): Flow<Response<List<Repository>>> = flow {
        try {
            emit(Response.Loading())
            val listRepository = repGitRepository.getAllRepositories().body()?.items?.map {
                Repository(
                    name = it.full_name,
                    author = it.owner.login,
                    image = it.owner.avatar_url,
                    stargazersCount = it.stargazers_count,
                    forksCount = it.forks_count
                )
            }
            emit(Response.Success(listRepository))
        }
        catch (e: HttpException){
            emit(Response.Error(e.printStackTrace().toString()))
        }
        catch (e: IOException){
            emit(Response.Error(e.printStackTrace().toString()))
        }
    }
}