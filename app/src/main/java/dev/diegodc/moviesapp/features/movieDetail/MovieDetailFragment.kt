package dev.diegodc.moviesapp.features.movieDetail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment() : BaseFragment(R.layout.fragment_movie_detail), IMovieDetailContract.IMovieDetailView {

    companion object {
        const val MOVIE_ID_ARG = "movie_id"
    }

    @Inject lateinit var presenterI: IMovieDetailContract.IMovieDetailPresenter<IMovieDetailContract.IMovieDetailView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenterI.onAttach(this)
        arguments?.let { bundle ->
            bundle.getLong(MOVIE_ID_ARG).let {
                presenterI.loadMovieDetails(it)
            }
        }
    }

    override fun initViews() {
    }

    override fun onMovieDetailLoaded(movie: MovieDetailedView) {

    }
}