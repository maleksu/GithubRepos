package com.df.githubrepos.domain.network

import com.df.githubrepos.domain.data.model.GithubCommitDto
import com.df.githubrepos.domain.data.model.GithubRepoDto
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("repos/{ownerrepo}/commits")
    fun getCommitsForRepo(@Path("ownerrepo", encoded = true) repo:String): Deferred<Response<List<GithubCommitDto>>>

    @GET("repos/{ownerrepo}")
    fun getRepo(@Path("ownerrepo", encoded = true) repo:String): Deferred<Response<GithubRepoDto>>
}