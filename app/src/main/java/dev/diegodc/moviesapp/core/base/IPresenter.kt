package dev.diegodc.moviesapp.core.base

import java.lang.Exception

interface IPresenter<V : IView> {
    fun onAttach(view: V)
    fun onDetach()
    fun handleError(error: Exception)
}