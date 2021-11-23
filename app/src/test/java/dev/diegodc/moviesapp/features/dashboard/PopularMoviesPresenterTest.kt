package dev.diegodc.moviesapp.features.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.diegodc.moviesapp.data.repository.MoviesRepository
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import dev.diegodc.moviesapp.features.dashboard.models.MovieView
import dev.diegodc.moviesapp.features.dashboard.screens.popular.PopularMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract
import dev.diegodc.moviesapp.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PopularMoviesPresenterTest {
    private val movieRepository: MoviesRepository = mockk()
    private val mView: IPopularMoviesContract.IPopularMoviesView = spyk()

    private val popularMoviePresenter: PopularMoviesPresenter<IPopularMoviesContract.IPopularMoviesView> by lazy {
        PopularMoviesPresenter(movieRepository)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        popularMoviePresenter.onAttach(mView)
    }

    @Test
    fun testGetPopularMovies() {
        val mockedMovies = (1..2).map { generateMockMovie(it) }
        coEvery {
            movieRepository.loadPopularMovies(page = 1, refresh = true)
        } answers {
            flow {
                emit(
                    Result.Success(
                        mockedMovies
                    )
                )
            }
        }

        popularMoviePresenter.loadMovies()

        coVerify{
            mView.onMoviesLoaded(
                mockedMovies.map {
                    MovieView(
                        it.id,
                        it.title,
                        it.poster_path ?: "",
                        it.popularity
                    )
                }
            )
        }
    }

    @Test
    fun testGetMultiplePages() {

    }

    private fun generateMockMovie(id: Int): MovieDB {
        return MovieDB(
            adult = false,
            backdrop_path = "",
            created_at = 0L,
            id = id * 1000L,
            original_title = "",
            original_language = "",
            overview = "",
            popularity = 0.0f,
            poster_path = "",
            release_date = 0L,
            vote_count = 0,
            vote_average = 0.0f,
            title = "Movie $id",
            video = false
        )
    }
}