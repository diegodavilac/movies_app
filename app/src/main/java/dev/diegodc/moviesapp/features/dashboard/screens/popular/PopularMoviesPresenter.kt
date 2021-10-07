package dev.diegodc.moviesapp.features.dashboard.screens.popular

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesPresenter<V : IPopularMoviesContract.IPopularMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : BasePresenter<V>(), IPopularMoviesContract.IPopularMoviesPresenter<V> {
    override fun loadMovies() {
        GlobalScope.launch {
            mView?.showLoading()
            when (val result = moviesRepository.loadPopularMovies(page = 1)) {
                is Result.Success -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        (mView as? IPopularMoviesContract.IPopularMoviesView)?.onMoviesLoaded(result.data.map {
                            MovieView(
                                it.id,
                                it.title,
                                it.poster,
                                it.popularity
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