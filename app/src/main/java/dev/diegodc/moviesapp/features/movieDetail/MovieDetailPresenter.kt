package dev.diegodc.moviesapp.features.movieDetail

import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailPresenter<V : IMovieDetailContract.IMovieDetailView> @Inject constructor(
    private val repository: IMoviesRepository
) : IMovieDetailContract.IMovieDetailPresenter<V>() {

    override fun loadMovieDetails(id: Long) {
        launch(Dispatchers.IO) {
            repository
                .getDetails(id)
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            mView?.hideLoading()
                            (mView as? IMovieDetailContract.IMovieDetailView)?.onMovieDetailLoaded(
                                result.data.let {
                                    MovieDetailedView(
                                        title = it.title,
                                        image = it.poster,
                                        id = it.id
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