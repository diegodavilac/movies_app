package dev.diegodc.moviesapp.features.movieDetail

import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract.IMovieDetailPresenter
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract.IMovieDetailView
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment() :
    BaseFragment<IMovieDetailView, IMovieDetailPresenter<IMovieDetailView>>(R.layout.fragment_movie_detail),
    IMovieDetailView {

    companion object {
        const val MOVIE_ID_ARG = "movie_id"
    }

    override fun initViews() {
        arguments?.let { bundle ->
            bundle.getLong(MOVIE_ID_ARG).let {
                presenter.loadMovieDetails(it)
            }
        }
    }

    override fun onMovieDetailLoaded(movie: MovieDetailedView) {
        launch {

        }
    }
}