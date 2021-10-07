package dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter

import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.dashboard.models.MovieView

interface IPopularMoviesContract {
    interface IPopularMoviesPresenter<V : IPopularMoviesView> : IPresenter<V>{
        fun loadMovies()
    }
    interface IPopularMoviesView : IView{
        fun onMoviesLoaded(movies: List<MovieView>)
    }
}