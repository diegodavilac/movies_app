package dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter

import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.dashboard.models.MovieView

interface ILatestMoviesContract {
    interface ILatestMoviesPresenter<V : ILatestMoviesView> : IPresenter<V>{
        fun loadMovies()
    }
    interface ILatestMoviesView : IView{
        fun onMovieLoaded(movie: MovieView)
    }
}