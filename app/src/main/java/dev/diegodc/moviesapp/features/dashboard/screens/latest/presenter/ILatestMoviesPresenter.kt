package dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.dashboard.models.MovieView

interface ILatestMoviesContract {
    abstract class ILatestMoviesPresenter<V : ILatestMoviesView> : BasePresenter<V>(){
        abstract fun loadMovies()
    }
    interface ILatestMoviesView : IView{
        fun onMovieLoaded(movie: MovieView)
    }
}