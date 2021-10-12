package dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter

import androidx.paging.PagingData
import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface IUpcomingMoviesContract {
    abstract class IUpcomingMoviesPresenter<V : IUpcomingMoviesView> : BasePresenter<V>() {
        abstract fun loadMovies()
    }
    interface IUpcomingMoviesView : IView{
        fun onMoviesLoaded(movies: PagingData<MovieView>)
    }
}