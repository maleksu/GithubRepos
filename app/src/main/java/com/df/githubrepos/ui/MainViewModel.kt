package com.df.githubrepos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.domain.data.model.GithubRepositoryModel
import com.df.githubrepos.domain.network.ApiResponse
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.filterIndexed
import kotlin.collections.joinToString
import kotlin.collections.set


class MainViewModel(private val repository: DataRepository) : ViewModel() {

    private val cachedRepos = HashMap<String, GithubRepositoryModel>()

    private val _selectedRepo = MutableLiveData<GithubRepositoryModel>()
    val selectedRepo: LiveData<GithubRepositoryModel>
        get() = _selectedRepo

    private val _textToShare = MutableLiveData<String>()
    val textToShare: LiveData<String>
        get() = _textToShare


    fun loadData(repo: String) {
        if (cachedRepos[repo] != null) {
            _selectedRepo.value = cachedRepos[repo]
        } else {
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

    fun prepareCommitsToShare(selectedCommitsIdx: HashSet<Int>) {
        viewModelScope.launch {

            val selectedCommits = selectedRepo.value?.commits
                ?.filterIndexed { index, _ ->
                selectedCommitsIdx.contains(index)
                }
                ?.joinToString(separator = ",\n") ?: ""
            _textToShare.value = selectedCommits
        }
    }
}