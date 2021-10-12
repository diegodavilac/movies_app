package dev.diegodc.moviesapp.features.movieDetail.contract

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.base.IPresenter
import dev.diegodc.moviesapp.core.base.IView
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView

interface IMovieDetailContract {
    abstract class IMovieDetailPresenter<V: IMovieDetailView> : BasePresenter<V>(){
        abstract fun loadMovieDetails(id: Long)
    }
    interface IMovieDetailView : IView{
        fun onMovieDetailLoaded(movie : MovieDetailedView)
    }
}