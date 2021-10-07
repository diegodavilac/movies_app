package dev.diegodc.moviesapp.features.dashboard.screens.latest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract.ILatestMoviesView
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract.ILatestMoviesPresenter
import dev.diegodc.moviesapp.features.movieDetail.MovieDetailFragment
import kotlinx.android.synthetic.main.fragment_lasted_movie.*

@AndroidEntryPoint
class LatestMoviesFragment :
    BaseFragment<ILatestMoviesView, ILatestMoviesPresenter<ILatestMoviesView>>(R.layout.fragment_lasted_movie),
    ILatestMoviesView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        presenter.loadMovies()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //Load movies when it is visible
        if (!hidden) {
            presenter.loadMovies()
        }
    }

    override fun initViews() {}

    override fun onMovieLoaded(movie: MovieView) {
        Log.d("MoviesApp", "onMoviesLoaded")
        textView_movieTitle.text = movie.title
        textView_movieRating.text = movie.rating.toString()
        textView_movieTitle_overview.text = movie.overview
        Glide
            .with(requireContext())
            .load(movie.url)
            .centerCrop()
            .placeholder(R.drawable.illustration_placeholder)
            .into(imageView_movie)

        button_latestMovie_continue.setOnClickListener {
            //Navigate to movie's detail
            findNavController().navigate(
                R.id.action_dashboardFragment_to_movieDetailFragment,
                Bundle().apply {
                    putLong(MovieDetailFragment.MOVIE_ID_ARG, movie.id)
                }
            )
        }
    }

    override fun showErrorMessage(message: String) {

    }
}