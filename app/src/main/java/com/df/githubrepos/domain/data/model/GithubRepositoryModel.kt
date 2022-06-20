package com.df.githubrepos.domain.data.model

data class GithubRepositoryModel(
    val repositoryName: String,
    val repositoryId: Long,
    val commits: List<GithubCommitModel>?

)
