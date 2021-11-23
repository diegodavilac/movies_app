package dev.diegodc.moviesapp.features.movieDetail

import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract.IMovieDetailPresenter
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract.IMovieDetailView
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.view.*
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

            textView_movieDetail_title.text = movie.title
            textView_movieDetail_overview.text = movie.overview

            Glide
                .with(requireContext())
                .load(movie.url)
                .centerCrop()
                .placeholder(R.drawable.illustration_placeholder)
                .into(imageView_movieDetail_poster)
        }
    }
}