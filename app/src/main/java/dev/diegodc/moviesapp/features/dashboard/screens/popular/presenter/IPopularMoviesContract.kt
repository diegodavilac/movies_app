package dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter

import androidx.paging.PagingData
import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface IPopularMoviesContract {
    abstract class IPopularMoviesPresenter<V : IPopularMoviesView> : BasePresenter<V>(){
        abstract fun loadMovies()
        abstract fun loadNextPage()
    }
    interface IPopularMoviesView : IView{
        fun onMoviesLoaded(movies: List<MovieView>)
    }
}