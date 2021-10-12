package dev.diegodc.moviesapp.features.dashboard.screens.latest

import android.util.Log
import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LatestMoviesPresenter<V : ILatestMoviesContract.ILatestMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : ILatestMoviesContract.ILatestMoviesPresenter<V>() {

    override fun loadMovies() {
        launch {
            moviesRepository
                .loadLatestMoviesFlow()
                .flowOn(Dispatchers.IO)
                .catch { cause ->
                    mView?.showErrorMessage(cause.message?:"")
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            mView?.hideLoading()
                            (mView as? ILatestMoviesContract.ILatestMoviesView)?.onMovieLoaded(
                                result.data.let {
                                    MovieView(
                                        it.id,
                                        it.title,
                                        it.poster,
                                        it.popularity,
                                        it.overview
                                    )
                                })
                        }
                        is Result.Loading -> {
                            mView?.showLoading()
                        }
                        is Result.Error -> {
                            handleError(result.exception)
                        }
                    }

                }
        }
    }

}