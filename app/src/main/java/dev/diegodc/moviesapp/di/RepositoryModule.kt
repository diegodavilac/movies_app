package dev.diegodc.moviesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.diegodc.moviesapp.BuildConfig
import dev.diegodc.moviesapp.data.repository.MoviesRepository
import dev.diegodc.moviesapp.data.sources.MoviesDataSource
import dev.diegodc.moviesapp.data.sources.remote.MoviesAPI
import dev.diegodc.moviesapp.data.sources.remote.MoviesRemoteDataSource
import dev.diegodc.moviesapp.data.sources.remote.util.AuthInterceptor
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesMoviesRepo(
        @Named("Remote") remoteDataSource: MoviesDataSource
    ) : IMoviesRepository = MoviesRepository(remoteDataSource = remoteDataSource)

    @Singleton
    @Provides
    @Named("Remote")
    fun provideRemoteDataSource(api : MoviesAPI) : MoviesDataSource = MoviesRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideMoviesAPI(retrofit: Retrofit) = retrofit.create(MoviesAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}