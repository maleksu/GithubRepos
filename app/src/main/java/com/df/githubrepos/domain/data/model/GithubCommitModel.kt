package com.df.githubrepos.domain.data.model

data class GithubCommitModel(
    val message: String,
    val sha: String,
    val author: String
){
    override fun toString(): String {
        return "$message $sha $author"
    }
}


