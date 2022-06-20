package com.df.githubrepos.domain.data.model

/**
 * Created by Piotr.Malak on 20/06/2022.

 */
data class GithubRepositoryModel(
    val repositoryName: String,
    val repositoryId: Long,
    val commits: List<GithubCommitModel>?

)
