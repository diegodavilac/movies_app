package dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter

import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.dashboard.models.MovieView

interface IUpcomingMoviesContract {
    interface IUpcomingMoviesPresenter<V : IUpcomingMoviesView> : IPresenter<V>{
        fun loadMovies()
    }
    interface IUpcomingMoviesView : IView{
        fun onMoviesLoaded(movies: List<MovieView>)
    }
}