package dev.diegodc.moviesapp.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dev.diegodc.moviesapp.data.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.movieDetail.MovieDetailFragment
import dev.diegodc.moviesapp.features.movieDetail.MovieDetailPresenter
import dev.diegodc.moviesapp.features.movieDetail.contract.IMovieDetailContract

@Module
@InstallIn(FragmentComponent::class)
class MovieDetailModule {

    @Provides
    fun provideFragment(fragment: Fragment): IMovieDetailContract.IMovieDetailView =
        fragment as MovieDetailFragment

    @Provides
    fun provideMovieDetailPresenter(
        repository: IMoviesRepository
    ): IMovieDetailContract.IMovieDetailPresenter<IMovieDetailContract.IMovieDetailView> {
        return MovieDetailPresenter<IMovieDetailContract.IMovieDetailView>(repository)
    }
}