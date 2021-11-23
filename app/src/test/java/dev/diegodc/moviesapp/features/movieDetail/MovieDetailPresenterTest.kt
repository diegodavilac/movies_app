package dev.diegodc.moviesapp.features.movieDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.repository.MoviesRepository
import dev.diegodc.moviesapp.domain.model.DetailedMovie
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract
import dev.diegodc.moviesapp.features.movieDetail.model.MovieDetailedView
import dev.diegodc.moviesapp.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailPresenterTest {

    private val movieRepository: MoviesRepository = mockk()
    private val mView: IMovieDetailContract.IMovieDetailView = spyk()

    private val movieDetailPresenter: MovieDetailPresenter<IMovieDetailContract.IMovieDetailView> by lazy {
        MovieDetailPresenter(movieRepository)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        movieDetailPresenter.onAttach(mView)
    }

    @Test
    fun testGetMovieDetail() {
        coEvery {
            movieRepository.getDetails(12345L)
        } returns flow {
            emit(
                Result.Success(
                    DetailedMovie(
                        poster = "",
                        title = "",
                        id = 12345L,
                        overview = "",
                        popularity = 0F
                    )
                )
            )
        }

        movieDetailPresenter.loadMovieDetails(12345L)
        coVerify(atLeast = 1, verifyBlock = {
            mView.onMovieDetailLoaded(
                MovieDetailedView(
                    image = "",
                    title = "",
                    id = 12345L,
                    overview = "",
                )
            )
        })
    }

    @Test
    fun testGetMovieDetailLoading() {
        coEvery {
            movieRepository.getDetails(12345L)
        } returns flow {
            emit(
                Result.Loading(
                    DetailedMovie(
                        poster = "",
                        title = "",
                        id = 12345L,
                        overview = "",
                        popularity = 0F
                    )
                )
            )
        }

        movieDetailPresenter.loadMovieDetails(12345L)
        coVerify {
            mView.showLoading()
        }
    }
}