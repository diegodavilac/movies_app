package dev.diegodc.moviesapp.core.model

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Loading<out T>(val data: T? = null) : Result<T>()
}