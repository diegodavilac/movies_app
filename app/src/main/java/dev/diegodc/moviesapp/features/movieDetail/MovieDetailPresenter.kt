package dev.diegodc.moviesapp.features.movieDetail

import dev.diegodc.moviesapp.core.base.BasePresenter
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView
import javax.inject.Inject

class MovieDetailPresenter<V: IMovieDetailContract.IMovieDetailView> @Inject constructor(
    private val repository: IMoviesRepository
) : BasePresenter<V>(), IMovieDetailContract.IMovieDetailPresenter<V>{

    override fun loadMovieDetails(id: Long) {
        mView?.showLoading()
        GlobalScope.launch(Dispatchers.IO) {
            when(val result = repository.getDetails(id)){
                is Result.Success -> {
                    GlobalScope.launch(Dispatchers.Main){
                        mView?.hideLoading()
                        (mView as? IMovieDetailContract.IMovieDetailView)?.onMovieDetailLoaded(result.data.let {
                            MovieDetailedView(
                                title = it.title,
                                image = it.poster,
                                id = it.id
                            )
                        })
                    }
                }
                is Result.Error ->{
                    handleError(result.exception)
                }
            }
        }
    }
}