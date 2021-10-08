package dev.diegodc.moviesapp.features.dashboard.screens.upcoming

import androidx.paging.cachedIn
import androidx.paging.map
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class UpcomingMoviesPresenter<V : IUpcomingMoviesContract.IUpcomingMoviesView> @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : IUpcomingMoviesContract.IUpcomingMoviesPresenter<V>() {

    override fun loadMovies() {
        launch {
            moviesRepository
                .loadUpcomingMovies()
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
                .flowOn(Dispatchers.IO)
                .cachedIn(this@UpcomingMoviesPresenter)
                .catch { cause: Throwable ->  handleError(Exception(cause)) }
                .collectLatest {
                    (mView as? IUpcomingMoviesContract.IUpcomingMoviesView)?.onMoviesLoaded(it)
                }
        }
    }

}