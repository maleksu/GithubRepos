package com.df.githubrepos.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.df.githubrepos.domain.data.model.GithubRepositoryModel
import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.domain.network.ApiResponse
import kotlinx.coroutines.launch


class MainViewModel(private val repository: DataRepository): ViewModel() {

    private val cachedRepos = HashMap<String, GithubRepositoryModel>()
    private val _selectedRepo = MutableLiveData<GithubRepositoryModel>()
    val selectedRepo : LiveData<GithubRepositoryModel>
        get() = _selectedRepo

    fun loadData(repo: String){
        if(cachedRepos[repo] !=null){
            _selectedRepo.value = cachedRepos[repo]
        }else {
            viewModelScope.launch {
                when (val result = repository.getRepo(repo)) {
                    is ApiResponse.Success -> {
                        cachedRepos[repo] = result.data
                        _selectedRepo.value = result.data
                    }
                    is ApiResponse.Error -> {
                        _selectedRepo.value = null
                    }
                }

            }
        }
    }
}