package dev.diegodc.moviesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.diegodc.moviesapp.BuildConfig
import dev.diegodc.moviesapp.core.util.NetworkUtil
import dev.diegodc.moviesapp.data.repository.MoviesRepository
import dev.diegodc.moviesapp.data.sources.db.MovieDatabase
import dev.diegodc.moviesapp.data.sources.db.dao.LatestMovieDao
import dev.diegodc.moviesapp.data.sources.db.dao.MovieDao
import dev.diegodc.moviesapp.data.sources.remote.MoviesAPI
import dev.diegodc.moviesapp.data.sources.remote.util.AuthInterceptor
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import dev.diegodc.moviesapp.data.sources.db.dao.RemoteKeyDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesMoviesRepo(
//        @Named("Remote") remoteDataSource: MoviesDataSource,
//        @Named("Local") localDataSource: MoviesDataSource,
        api: MoviesAPI,
        movieDao: MovieDao,
        networkUtil: NetworkUtil,
        remoteKeyDao: RemoteKeyDao,
        latestMovieDao: LatestMovieDao
    ): IMoviesRepository = MoviesRepository(
//        remoteDataSource = remoteDataSource,
//        localDataSource = localDataSource,
        api,
        movieDao,
        networkUtil,
        latestMovieDao,
        remoteKeyDao
    )

//    @Singleton
//    @Provides
//    @Named("Remote")
//    fun provideRemoteDataSource(api: MoviesAPI): MoviesDataSource = MoviesRemoteDataSource(api)
//
//    @Singleton
//    @Provides
//    @Named("Local")
//    fun provideLocalDataSource(movieDao: MovieDao): MoviesDataSource =
//        MovieLocalDataSource(movieDao)


    @Singleton
    @Provides
    fun providesLatestMoviesDao(database: MovieDatabase): LatestMovieDao {
        return database.latestMovieDao()
    }

    @Singleton
    @Provides
    fun providesMoviesDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Singleton
    @Provides
    fun providesRemoteKeyDao(database: MovieDatabase): RemoteKeyDao {
        return database.remoteKeyDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            applicationContext, MovieDatabase::class.java, "movies-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMoviesAPI(retrofit: Retrofit) : MoviesAPI = retrofit.create(MoviesAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkUtil(@ApplicationContext applicationContext: Context): NetworkUtil =
        NetworkUtil(applicationContext)
}