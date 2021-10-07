package dev.diegodc.moviesapp.core.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dev.diegodc.moviesapp.core.ui.hideLoadingDialog
import dev.diegodc.moviesapp.core.ui.showLoadingDialog
import javax.inject.Inject

abstract class BaseFragment<V: IView, P: IPresenter<V>>(@LayoutRes view : Int) : Fragment(view), IView{

    abstract fun initViews()

    @Inject
    open lateinit var presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this@BaseFragment as V)
        initViews()
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        hideLoadingDialog()
    }

    override fun isNetworkConnected(): Boolean {
        Log.d("MoviesApp", "isNetworkConnected")
        return true
    }

    override fun showErrorMessage(message: String) {
        TODO("Not yet implemented")
    }
}