package dev.diegodc.moviesapp.features.dashboard.screens.popular

import androidx.paging.cachedIn
import androidx.paging.map
import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.repository.IMoviesRepository
import dev.diegodc.moviesapp.domain.model.SimpleMovie
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PopularMoviesPresenter<V : IPopularMoviesContract.IPopularMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : IPopularMoviesContract.IPopularMoviesPresenter<V>() {

    override fun loadMovies() {
        launch {
            moviesRepository.loadPopularMovies()
                .map { pagingData ->
                    pagingData.map {
                        MovieView(
                            it.id,
                            it.title,
                            it.poster_path?:"",
                            it.popularity
                        )
                    }
                }
                .cachedIn(this@PopularMoviesPresenter)
                .catch { throwable ->
                    handleError(Exception(throwable))
                }
                .collectLatest {
                    (mView as? IPopularMoviesContract.IPopularMoviesView)?.onMoviesLoaded(it)
                }
        }
    }

}