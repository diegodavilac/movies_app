package dev.diegodc.moviesapp.features.dashboard.screens.upcoming

import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMoviesPresenter<V : IUpcomingMoviesContract.IUpcomingMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : IUpcomingMoviesContract.IUpcomingMoviesPresenter<V>() {

    private var page = 1

    override fun loadMovies() {
        launch {
            moviesRepository.loadUpcomingMovies(
                page = page,
                refresh = true
            ).collectLatest { result ->
                when (result) {
                    is Result.Loading -> mView?.showLoading()
                    is Result.Success -> {
                        mView?.hideLoading()
                        (mView as? IUpcomingMoviesContract.IUpcomingMoviesView)?.onMoviesLoaded(
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
            moviesRepository.loadUpcomingMovies(
                page = page + 1,
                refresh = false
            ).collectLatest { result ->
                when (result) {
                    is Result.Loading -> mView?.showLoading()
                    is Result.Success -> {
                        mView?.hideLoading()
                        (mView as? IUpcomingMoviesContract.IUpcomingMoviesView)?.onMoviesLoaded(
                            result.data.map {
                                MovieView(
                                    it.id,
                                    it.title,
                                    it.poster_path ?: "",
                                    it.popularity
                                )
                            }
                        )
                        if (result.data.isNotEmpty()) page++
                    }
                    is Result.Error -> {
                        handleError(result.exception)
                    }
                }

            }
        }
    }

}