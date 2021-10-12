package dev.diegodc.moviesapp.features.dashboard.screens.upcoming

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.core.util.OnScrollLoadMore
import dev.diegodc.moviesapp.features.dashboard.adapter.MoviesAdapter
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract.IUpcomingMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract.IUpcomingMoviesView
import dev.diegodc.moviesapp.features.movieDetail.MovieDetailFragment
import kotlinx.android.synthetic.main.fragment_movies_listed.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMoviesFragment :
    BaseFragment<IUpcomingMoviesView, IUpcomingMoviesPresenter<IUpcomingMoviesView>>(R.layout.fragment_movies_listed),
    IUpcomingMoviesView {

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //Load movies when it is visible
        if (!hidden) {
            presenter.loadMovies()
        }
    }

    override fun initViews() {
        if (isVisible) presenter.loadMovies()

        recyclerView_movies.adapter = MoviesAdapter() { movie ->
            //Navigate to movie's detail
            findNavController().navigate(
                R.id.action_dashboardFragment_to_movieDetailFragment,
                Bundle().apply {
                    putLong(MovieDetailFragment.MOVIE_ID_ARG, movie.id)
                }
            )
        }

        recyclerView_movies.addOnScrollListener(
            OnScrollLoadMore(recyclerView_movies.layoutManager as GridLayoutManager){
                presenter.loadNextPage()
            }
        )
    }

    override fun onMoviesLoaded(movies: List<MovieView>) {
        launch {
            Log.d("MoviesApp", "onMoviesLoaded")
            (recyclerView_movies.adapter as MoviesAdapter).apply {
                val currentIndex = data.size
                data.addAll(movies)
                notifyItemRangeInserted(currentIndex, movies.size)
            }
        }

    }

}