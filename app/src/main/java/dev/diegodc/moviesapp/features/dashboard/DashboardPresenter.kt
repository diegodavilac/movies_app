package dev.diegodc.moviesapp.features.dashboard

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.features.dashboard.contract.IDashboardContract
import javax.inject.Inject

class DashboardPresenter<V : IDashboardContract.IDashboardView> @Inject constructor() : BasePresenter<V>(),
    IDashboardContract.IDashboardPresenter<V> {
        private var viewInit : Boolean = false
        val isViewInitializated : Boolean get() = viewInit

    override fun initContent() {
        if (!viewInit){
            (mView as? IDashboardContract.IDashboardView)?.initContent()
            viewInit = true
        }
    }

}