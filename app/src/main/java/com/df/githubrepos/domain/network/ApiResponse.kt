package com.df.githubrepos.domain.network

sealed class ApiResponse<out T:Any> {
    data class Success<out T: Any>(val data: T): ApiResponse<T>()
    data class Error(val exception: String): ApiResponse<Nothing>()
}