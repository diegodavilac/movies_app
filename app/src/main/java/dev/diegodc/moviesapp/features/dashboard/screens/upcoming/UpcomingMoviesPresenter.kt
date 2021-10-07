package dev.diegodc.moviesapp.features.dashboard.screens.upcoming

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMoviesPresenter<V : IUpcomingMoviesContract.IUpcomingMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : BasePresenter<V>(), IUpcomingMoviesContract.IUpcomingMoviesPresenter<V> {

    private var page: Int = 1

    override fun loadMovies() {
        GlobalScope.launch {
            mView?.showLoading()
            when (val result = moviesRepository.loadUpcomingMovies(page = page)) {
                is Result.Success -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        (mView as? IUpcomingMoviesContract.IUpcomingMoviesView)?.onMoviesLoaded(
                            result.data.map {
                                MovieView(
                                    it.id,
                                    it.title,
                                    it.poster,
                                    it.popularity
                                )
                            })
                        mView?.hideLoading()
                    }
                    page++
                }
                is Result.Error -> {
                }
            }
        }
    }

}