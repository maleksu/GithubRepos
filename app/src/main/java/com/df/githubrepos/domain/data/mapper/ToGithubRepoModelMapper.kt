package com.df.githubrepos.domain.data.mapper

import com.df.githubrepos.domain.data.model.GithubCommitDto
import com.df.githubrepos.domain.data.model.GithubCommitModel
import com.df.githubrepos.domain.data.model.GithubRepoDto
import com.df.githubrepos.domain.data.model.GithubRepositoryModel

class ToGithubRepoModelMapper {
    fun map(repo: GithubRepoDto, commits: List<GithubCommitDto>?) =
        GithubRepositoryModel(
            repositoryName = repo.name,
            repositoryId = repo.id,
            commits = commits?.sortedByDescending { it.commit.author.date }
                ?.map { it.toGithubCommitModel() })

    private fun GithubCommitDto.toGithubCommitModel() =
        GithubCommitModel(this.commit.message, this.sha, this.commit.author.name)
}