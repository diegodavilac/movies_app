package dev.diegodc.moviesapp.features.movieDetail.contract

import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView

interface IMovieDetailContract {
    interface IMovieDetailPresenter<V: IMovieDetailView> : IPresenter<V>{
        fun loadMovieDetails(id: Long)
    }
    interface IMovieDetailView : IView{
        fun onMovieDetailLoaded(movie : MovieDetailedView)
    }
}