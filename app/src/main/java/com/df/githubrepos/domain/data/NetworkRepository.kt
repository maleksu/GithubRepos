package com.df.githubrepos.domain.data

import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.domain.data.mapper.ToGithubRepoModelMapper
import com.df.githubrepos.domain.data.model.GithubRepositoryModel
import com.df.githubrepos.domain.network.ApiResponse
import com.df.githubrepos.domain.network.GithubApi

internal class NetworkRepository
constructor(private val service: GithubApi, private val mapper: ToGithubRepoModelMapper) :
    DataRepository {
    override suspend fun getRepo(repo: String): ApiResponse<GithubRepositoryModel> {
        return try {
            val repository = service.getRepoAsync(repo).await()
            val commits = service.getCommitsForRepoAsync(repo).await()
            if (repository.isSuccessful && commits.isSuccessful) {
                repository.body()?.let {
                    ApiResponse.Success(mapper.map(it, commits.body()))
                } ?: run {
                    ApiResponse.Error("no repo found")
                }
            } else {
                ApiResponse.Error("communication error: ${repository.errorBody()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("error: ${e.localizedMessage}")
        }
    }

}