package com.df.githubrepos.data.model

data class CommitDto(
    val author: AuthorDto,
    val message: String,
)