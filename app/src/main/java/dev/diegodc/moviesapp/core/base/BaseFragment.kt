package dev.diegodc.moviesapp.core.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dev.diegodc.moviesapp.core.ui.hideLoadingDialog
import dev.diegodc.moviesapp.core.ui.showLoadingDialog
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<V: IView, P: BasePresenter<V>>(@LayoutRes view : Int) : Fragment(view), CoroutineScope by MainScope(), IView{

    abstract fun initViews()

    @Inject
    open lateinit var presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this@BaseFragment as V)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        if (!presenter.isPresenterAttached()) {
            presenter.onAttach(this@BaseFragment as V)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.onDetach()
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        if (presenter.isPresenterAttached()) {
            presenter.onDetach()
        }
        super.onDestroyView()
    }

    override fun showLoading() {
        launch {
            showLoadingDialog()
        }
    }

    override fun hideLoading() {
        launch {
            hideLoadingDialog()
        }
    }

    override fun isNetworkConnected(): Boolean {
        return true
    }

    override fun showErrorMessage(message: String) {

    }
}