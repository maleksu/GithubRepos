package com.df.githubrepos.domain.data.model

data class CommitDto(
    val author: AuthorDto,
    val message: String,
)