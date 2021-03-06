package dev.diegodc.moviesapp.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.features.dashboard.DashboardFragment
import dev.diegodc.moviesapp.features.dashboard.DashboardPresenter
import dev.diegodc.moviesapp.features.dashboard.contract.IDashboardContract
import dev.diegodc.moviesapp.features.dashboard.screens.latest.LatestMoviesFragment
import dev.diegodc.moviesapp.features.dashboard.screens.latest.LatestMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.latest.presenter.ILatestMoviesContract
import dev.diegodc.moviesapp.features.dashboard.screens.popular.PopularMoviesFragment
import dev.diegodc.moviesapp.features.dashboard.screens.popular.PopularMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.popular.presenter.IPopularMoviesContract
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.UpcomingMoviesFragment
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.UpcomingMoviesPresenter
import dev.diegodc.moviesapp.features.dashboard.screens.upcoming.presenter.IUpcomingMoviesContract

@Module
@InstallIn(FragmentComponent::class)
abstract class DashboardModule {
    companion object {
        @Provides
        fun provideFragmentLatestMovies(
            fragment: Fragment
        ) : ILatestMoviesContract.ILatestMoviesView = fragment as LatestMoviesFragment

        @Provides
        fun provideFragmentPopularMovies(
            fragment: Fragment
        ) : IPopularMoviesContract.IPopularMoviesView = fragment as PopularMoviesFragment

        @Provides
        fun provideFragmentUpcomingMovies(
            fragment: Fragment
        ) : IUpcomingMoviesContract.IUpcomingMoviesView = fragment as UpcomingMoviesFragment

        @Provides
        fun provideDashboardFragment(
            fragment: Fragment
        ) : IDashboardContract.IDashboardView = fragment as DashboardFragment

        @Provides
        fun providePopularMoviesPresenter(
            repository: IMoviesRepository
        ): IPopularMoviesContract.IPopularMoviesPresenter<IPopularMoviesContract.IPopularMoviesView>{
            return PopularMoviesPresenter<IPopularMoviesContract.IPopularMoviesView>(repository)
        }

        @Provides
        fun provideUpcomingMoviesPresenter(
            repository: IMoviesRepository
        ): IUpcomingMoviesContract.IUpcomingMoviesPresenter<IUpcomingMoviesContract.IUpcomingMoviesView>{
            return UpcomingMoviesPresenter<IUpcomingMoviesContract.IUpcomingMoviesView>(repository)
        }
    }

    @Binds
    abstract fun provideLatestMoviesPresenter(
        presenter: LatestMoviesPresenter<ILatestMoviesContract.ILatestMoviesView>
    ): ILatestMoviesContract.ILatestMoviesPresenter<ILatestMoviesContract.ILatestMoviesView>

    @Binds
    abstract fun provideDashboardPresenter(
        presenter: DashboardPresenter<IDashboardContract.IDashboardView>
    ): IDashboardContract.IDashboardPresenter<IDashboardContract.IDashboardView>

}