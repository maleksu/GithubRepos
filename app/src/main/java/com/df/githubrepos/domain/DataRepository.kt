package com.df.githubrepos.domain

import com.df.githubrepos.domain.data.model.GithubRepositoryModel
import com.df.githubrepos.domain.network.ApiResponse

interface DataRepository {
    suspend fun getRepo(repo: String):ApiResponse<GithubRepositoryModel>
}