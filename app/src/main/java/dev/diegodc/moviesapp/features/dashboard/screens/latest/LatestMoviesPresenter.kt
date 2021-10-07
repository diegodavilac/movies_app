package dev.diegodc.moviesapp.features.dashboard.screens.latest

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LatestMoviesPresenter<V : ILatestMoviesContract.ILatestMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : BasePresenter<V>(), ILatestMoviesContract.ILatestMoviesPresenter<V> {
    override fun loadMovies() {
        GlobalScope.launch {
            mView?.showLoading()
            when (val result = moviesRepository.loadLatestMovies()) {
                is Result.Success -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        (mView as? ILatestMoviesContract.ILatestMoviesView)?.onMovieLoaded(result.data.let {
                            MovieView(
                                it.id,
                                it.title,
                                it.poster,
                                it.popularity,
                                it.overview
                            )
                        })
                        mView?.hideLoading()
                    }
                }
                is Result.Error -> {
                    handleError(result.exception)
                }
            }
        }
    }

}