package com.df.githubrepos.data.model

data class GithubCommitDto(
    val commit: CommitDto,
    val sha: String
)