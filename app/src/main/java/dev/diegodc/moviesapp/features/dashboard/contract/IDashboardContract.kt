package dev.diegodc.moviesapp.features.dashboard.contract

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView

interface IDashboardContract {
    abstract class IDashboardPresenter<V: IView> : BasePresenter<V>(){
        abstract fun initContent()
    }
    interface IDashboardView : IView {
        fun initContent()
    }
}