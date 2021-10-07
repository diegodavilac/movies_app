package dev.diegodc.moviesapp.features.dashboard.screens.upcoming

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.features.dashboard.adapter.MoviesAdapter
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract
import dev.diegodc.moviesapp.features.movieDetail.MovieDetailFragment
import kotlinx.android.synthetic.main.fragment_movies_listed.*
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingMoviesFragment : BaseFragment(R.layout.fragment_movies_listed),
    IUpcomingMoviesContract.IUpcomingMoviesView {

    @Inject
    lateinit var presenter: IUpcomingMoviesContract.IUpcomingMoviesPresenter<IUpcomingMoviesContract.IUpcomingMoviesView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        if (isVisible) presenter.loadMovies()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //Load movies when it is visible
        if (!hidden) {
            if ((recyclerView_movies.adapter as MoviesAdapter).data.isEmpty())
                presenter.loadMovies()
        }
    }

    override fun initViews() {
        recyclerView_movies.adapter = MoviesAdapter() {movie ->
            //Navigate to movie's detail
            findNavController().navigate(
                R.id.action_dashboardFragment_to_movieDetailFragment,
                Bundle().apply {
                    putLong(MovieDetailFragment.MOVIE_ID_ARG, movie.id)
                }
            )
        }
    }

    override fun onMoviesLoaded(movies: List<MovieView>) {
        Log.d("MoviesApp", "onMoviesLoaded")
        (recyclerView_movies.adapter as MoviesAdapter).apply {
            data.addAll(movies)
            notifyDataSetChanged()
        }
    }

    override fun showErrorMessage(message: String) {

    }
}