package dev.diegodc.moviesapp.core.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dev.diegodc.moviesapp.core.ui.hideLoadingDialog
import dev.diegodc.moviesapp.core.ui.showLoadingDialog

abstract class BaseFragment(@LayoutRes view : Int) : Fragment(view), IView{

    abstract fun initViews()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun showLoading() {
        Log.d("MoviesApp", "showLoading")
        showLoadingDialog()
    }

    override fun hideLoading() {
        Log.d("MoviesApp", "hideLoading")
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