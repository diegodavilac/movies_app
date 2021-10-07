package dev.diegodc.moviesapp.features.dashboard.contract

import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView

interface IDashboardContract {
    interface IDashboardPresenter<V: IView> : IPresenter<V>{
        fun initContent()
    }
    interface IDashboardView : IView {
        fun initContent()
    }
}