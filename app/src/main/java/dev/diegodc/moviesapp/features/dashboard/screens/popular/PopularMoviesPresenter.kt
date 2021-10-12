package dev.diegodc.moviesapp.features.dashboard.screens.popular

import android.util.Log
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesPresenter<V : IPopularMoviesContract.IPopularMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : IPopularMoviesContract.IPopularMoviesPresenter<V>() {

    private var page = 1
    var isLoading: Boolean = false

    override fun loadMovies() {
        launch {
            moviesRepository.loadPopularMovies(
                page = page,
                refresh = true
            ).collectLatest { result ->
                when (result) {
                    is Result.Loading -> mView?.showLoading()
                    is Result.Success -> {
                        mView?.hideLoading()
                        Log.d(
                            "PopularMoviesPresenter",
                            "Page: $page \n Initial Movie: ${result.data.first().title} \n Last Movie: ${result.data.last().title}"
                        )
                        (mView as? IPopularMoviesContract.IPopularMoviesView)?.onMoviesLoaded(
                            result.data.map {
                                MovieView(
                                    it.id,
                                    it.title,
                                    it.poster_path ?: "",
                                    it.popularity
                                )
                            }
                        )
                    }
                    is Result.Error -> handleError(result.exception)
                }

            }
        }
    }

    override fun loadNextPage() {
        launch {
            if (!isLoading) {
                moviesRepository.loadPopularMovies(
                    page = page + 1,
                    refresh = false
                ).collectLatest { result ->
                        when (result) {
                            is Result.Loading -> {
                                isLoading = true
                                mView?.showLoading()
                            }
                            is Result.Success -> {
                                isLoading = false
                                mView?.hideLoading()
                                Log.d(
                                    "PopularMoviesPresenter",
                                    "Page: ${page + 1} \n Initial Movie: ${result.data.first().title} \n Last Movie: ${result.data.last().title}"
                                )
                                (mView as? IPopularMoviesContract.IPopularMoviesView)?.onMoviesLoaded(
                                    result.data.map {
                                        MovieView(
                                            it.id,
                                            it.title,
                                            it.poster_path ?: "",
                                            it.popularity
                                        )
                                    }
                                )
                                if (result.data.isNotEmpty()) page ++
                            }
                            is Result.Error -> {
                                isLoading = false
                                handleError(result.exception)
                            }
                        }

                    }
            }

        }
    }

}