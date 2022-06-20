package com.df.githubrepos.domain.data.model

data class GithubCommitDto(
    val commit: CommitDto,
    val sha: String
)