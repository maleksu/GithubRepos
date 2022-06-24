package com.df.githubrepos.di

import com.df.githubrepos.domain.data.NetworkRepository
import com.df.githubrepos.domain.data.mapper.ToGithubRepoModelMapper
import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.domain.network.GithubApi
import com.df.githubrepos.domain.network.RetrofitFactory
import com.df.githubrepos.extras.Constants.BASE_GITHUB_URL
import com.df.githubrepos.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single {RetrofitFactory().getRetrofitForApi(BASE_GITHUB_URL).create(
        GithubApi::class.java
    )}
    single { ToGithubRepoModelMapper() }
    single<DataRepository>{ NetworkRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
}
