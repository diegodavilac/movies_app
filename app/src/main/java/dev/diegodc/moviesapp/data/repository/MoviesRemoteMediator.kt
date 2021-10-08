package dev.diegodc.moviesapp.data.repository

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.diegodc.moviesapp.data.model.Movie
import dev.diegodc.moviesapp.data.model.MoviesResponse
import dev.diegodc.moviesapp.data.sources.db.dao.RemoteKeyDao
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import dev.diegodc.moviesapp.data.sources.db.model.RemoteKey
import java.lang.Exception

@OptIn(androidx.paging.ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val remoteKeyDao: RemoteKeyDao,
    private val saveMovies: (List<Movie>) -> Unit,
    private val remoteLoad: suspend (Int) -> MoviesResponse,
) : RemoteMediator<Int, MovieDB>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDB>
    ): MediatorResult {
        return try {
            Log.d("MoviesRemoteMediator","LoadType : ${loadType.name}")
            val pageKeyData  = getKeyPageData(loadType, state)

            val page = when (pageKeyData) {
                is MediatorResult.Success -> {
                    return pageKeyData
                }
                else -> {
                    pageKeyData as Int
                }
            }
//                when (loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> {
//                    Log.d("MoviesRemoteMediator","Prepend State: ${state.pages.last().nextKey}")
//                    return MediatorResult.Success(endOfPaginationReached = state.pages.last().nextKey == null)
//                }
//                LoadType.APPEND -> {
//                    Log.d("MoviesRemoteMediator","State: ${state.lastItemOrNull()}")
//                    state.lastItemOrNull()
//                        ?: return MediatorResult.Success(
//                            endOfPaginationReached = true
//                        )
//                    Log.d("MoviesRemoteMediator","State next key: ${state.pages.last().nextKey}")
//                    Log.d("MoviesRemoteMediator","State prev key: ${state.pages.last().prevKey}")
//                    state.pages.last().nextKey
//                }
//            }

            Log.d("MoviesRemoteMediator","Load Key: $page")
            val response = remoteLoad.invoke(page)
            if (loadType == LoadType.REFRESH) {
                remoteKeyDao.deleteAll()
            }

            val prevKey = if (page == MoviesRepository.STARTED_PAGE) null else page - 1
            val nextKey = if (response.isNextPageAvailable) page +1  else null
            val keys = response.results.map {
                RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
            }
            remoteKeyDao.insertAll(keys)
            saveMovies.invoke(response.results)

            Log.d("MoviesRemoteMediator","Load response: ${response.isNextPageAvailable}")
            MediatorResult.Success(
                endOfPaginationReached = !response.isNextPageAvailable
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieDB>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MoviesRepository.STARTED_PAGE
            }
            LoadType.APPEND -> {
                Log.d("MoviesRemoteMediator","State: ${state.lastItemOrNull()}")

                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                Log.d("MoviesRemoteMediator","State next key: ${state.pages.last().nextKey}")
                Log.d("MoviesRemoteMediator","State prev key: ${state.pages.last().prevKey}")
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                Log.d("MoviesRemoteMediator","Prepend State: ${state.pages.last().nextKey}")

                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieDB>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeyDao.remoteKeysCatId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieDB>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> remoteKeyDao.remoteKeysCatId(movie.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieDB>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> remoteKeyDao.remoteKeysCatId(movie.id) }
    }

}