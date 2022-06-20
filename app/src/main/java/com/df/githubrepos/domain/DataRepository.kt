package com.df.githubrepos.domain

import com.df.githubrepos.data.model.GithubRepoDto
import com.df.githubrepos.domain.network.ApiResponse

interface DataRepository {
    suspend fun getRepo(repo: String):ApiResponse<GithubRepoDto>
}