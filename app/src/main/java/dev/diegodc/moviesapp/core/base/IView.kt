package dev.diegodc.moviesapp.core.base

import kotlinx.coroutines.CoroutineScope

interface IView : CoroutineScope{
    fun showLoading()
    fun hideLoading()
    fun isNetworkConnected() : Boolean
    fun showErrorMessage(message: String)
}