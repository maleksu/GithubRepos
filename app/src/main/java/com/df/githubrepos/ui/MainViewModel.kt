package com.df.githubrepos.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.df.githubrepos.domain.DataRepository
import com.df.githubrepos.domain.network.ApiResponse
import kotlinx.coroutines.launch


class MainViewModel(private val repository: DataRepository): ViewModel() {

    fun loadData(repo: String){
        viewModelScope.launch {
            val result = repository.getRepo(repo)
                Log.d("GITREP", result.toString())
        }
    }
}