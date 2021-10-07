package dev.diegodc.moviesapp.features.movieDetail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract.IMovieDetailPresenter
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract.IMovieDetailView
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView

@AndroidEntryPoint
class MovieDetailFragment() :
    BaseFragment<IMovieDetailView, IMovieDetailPresenter<IMovieDetailView>>(R.layout.fragment_movie_detail),
    IMovieDetailView {

    companion object {
        const val MOVIE_ID_ARG = "movie_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            bundle.getLong(MOVIE_ID_ARG).let {
                presenter.loadMovieDetails(it)
            }
        }
    }

    override fun initViews() {
    }

    override fun onMovieDetailLoaded(movie: MovieDetailedView) {

    }
}