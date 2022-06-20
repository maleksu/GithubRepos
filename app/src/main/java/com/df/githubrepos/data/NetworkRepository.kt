package com.df.githubrepos.data

import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.data.model.GithubRepoDto
import com.df.githubrepos.domain.network.ApiResponse
import com.df.githubrepos.domain.network.GithubApi
import java.lang.Exception

internal class NetworkRepository
constructor(private val service: GithubApi) : DataRepository{
    override suspend fun getRepo(repo: String): ApiResponse<GithubRepoDto> {
        return try {
            val repository = service.getRepo(repo).await()
            val commits = service.getCommitsForRepo(repo).await()
            if( repository.isSuccessful && commits.isSuccessful) {
                repository.body()?.let {
                    ApiResponse.Success(it)
                }?: run {
                    ApiResponse.Error("no repo found")
                }
            }else{
                ApiResponse.Error("communication error: ${repository.errorBody()}")
            }
        }catch (e:Exception){
            ApiResponse.Error("error: ${e.localizedMessage}")
        }
    }

}