package dev.diegodc.moviesapp.core.base

import android.util.Log
import java.lang.Exception

open class BasePresenter<V: IView> : IPresenter<V>{

    var mView: IView? = null

    override fun onAttach(view: V) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    override fun handleError(error: Exception) {
        mView?.hideLoading()
        Log.e("BasePresenter", error.javaClass.name)
        Log.e("BasePresenter", error.message?:"")
        mView?.showErrorMessage(
            when(error){
                else -> "Something went wrong"
            }
        )
    }
}