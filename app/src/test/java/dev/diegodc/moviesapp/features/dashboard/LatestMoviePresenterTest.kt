package dev.diegodc.moviesapp.features.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.repository.MoviesRepository
import dev.diegodc.moviesapp.domain.model.SimpleMovie
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.latest.LatestMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract
import dev.diegodc.moviesapp.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LatestMoviePresenterTest {
    private val movieRepository: MoviesRepository = mockk()

    private val mView: ILatestMoviesContract.ILatestMoviesView = spyk(
        recordPrivateCalls = true,
    )

    private val latestMoviePresenter: LatestMoviesPresenter<ILatestMoviesContract.ILatestMoviesView> by lazy {
        LatestMoviesPresenter(movieRepository)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        latestMoviePresenter.onAttach(mView)
    }

    @Test
    fun testLoadLatestMovie() {
        coEvery {
            movieRepository.loadLatestMoviesFlow()
        } returns flow {
            emit(
                Result.Success(
                    SimpleMovie(
                        poster = "",
                        title = "",
                        id = 12345L,
                        overview = "",
                        popularity = 0F
                    )
                )
            )
        }

        latestMoviePresenter.loadMovies()

        coVerify {
            mView.onMovieLoaded(
                MovieView(
                    image = "",
                    title = "",
                    id = 12345L,
                    overview = "",
                    rating = 0F
                )
            )
        }
    }
}