package dev.diegodc.moviesapp.features.dashboard.screens.popular

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.diegodc.moviesapp.R
import dev.diegodc.moviesapp.core.base.BaseFragment
import dev.diegodc.moviesapp.core.util.OnScrollLoadMore
import dev.diegodc.moviesapp.features.dashboard.adapter.MoviesAdapter
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract.IPopularMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract.IPopularMoviesView
import dev.diegodc.moviesapp.features.movieDetail.MovieDetailFragment
import kotlinx.android.synthetic.main.fragment_movies_listed.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment :
    BaseFragment<IPopularMoviesView, IPopularMoviesPresenter<IPopularMoviesView>>(R.layout.fragment_movies_listed),
    IPopularMoviesView {

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
                Log.d(
                    "PopularMoviesFragment",
                    "OnScrollLoadMore: ${(presenter as PopularMoviesPresenter).isLoading}"
                )
                presenter.loadNextPage()
            }
        )
    }

    override fun onMoviesLoaded(movies: List<MovieView>) {
        launch {
            Log.d("PopularMoviesFragment", "onMoviesLoaded")
            (recyclerView_movies.adapter as? MoviesAdapter)?.apply {
                val currentIndex = data.size
                data.addAll(movies)
                notifyItemRangeInserted(currentIndex, movies.size)
            }
        }
    }

}