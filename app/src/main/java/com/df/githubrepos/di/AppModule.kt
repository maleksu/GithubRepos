package com.df.githubrepos.di

import com.df.githubrepos.data.NetworkRepository
import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.domain.network.GithubApi
import com.df.githubrepos.domain.network.RetrofitFactory
import com.df.githubrepos.extras.Constants.BASE_GITHUB_URL
import com.df.githubrepos.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val githubApi = RetrofitFactory().getRetrofitForApi(BASE_GITHUB_URL).create(
    GithubApi::class.java
)

val appModule = module {
    single<DataRepository>{NetworkRepository(githubApi)}
    viewModel { MainViewModel(get()) }
}