package dev.diegodc.moviesapp.core.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

open class BasePresenter<V: IView> : IPresenter<V>, CoroutineScope{

    protected var job : Job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    var mView: IView? = null

    override fun onAttach(view: V) {
        mView = view

    }

    override fun onDetach() {
        mView = null
    }

    fun isPresenterAttached() : Boolean = mView != null

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