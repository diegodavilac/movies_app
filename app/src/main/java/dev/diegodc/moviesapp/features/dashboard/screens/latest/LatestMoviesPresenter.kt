package dev.diegodc.moviesapp.features.dashboard.screens.latest

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LatestMoviesPresenter<V : ILatestMoviesContract.ILatestMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : BasePresenter<V>(), ILatestMoviesContract.ILatestMoviesPresenter<V> {
    override fun loadMovies() {
        GlobalScope.launch {
            mView?.showLoading()
            moviesRepository
                .loadLatestMoviesFlow()
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    withContext(Dispatchers.Main){
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

}