package dev.diegodc.moviesapp.data.sources.db.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.diegodc.moviesapp.data.sources.db.dao.MovieDao

//class MovieDBPagingSource(
//    private val movieDao: MovieDao,
//    private val type: Type
//): PagingSource<Int, MovieDB>() {
//
//    companion object {
//        const val INITIAL_PAGE_INDEX = 0
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MovieDB>): Int? = state.anchorPosition
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDB> {
//        val position = params.key ?: INITIAL_PAGE_INDEX
//
//        val movies = when( type) {
//            Type.POPULAR -> movieDao.getPopularMovies(params.loadSize)
//            Type.UPCOMING -> movieDao.getUpcomingMovies(params.loadSize)
//        }
//        return LoadResult.Page(
//            data = movies,
//            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//            nextKey = if (movies.isNullOrEmpty()) null else position + 1
//        )
//    }
//
//    enum class Type{
//        POPULAR, UPCOMING
//    }
//}