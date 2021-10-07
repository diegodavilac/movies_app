package dev.diegodc.moviesapp.core.base

interface IView {
    fun showLoading()
    fun hideLoading()
    fun isNetworkConnected() : Boolean
    fun showErrorMessage(message: String)
}