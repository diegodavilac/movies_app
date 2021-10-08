package dev.diegodc.moviesapp.data.util

import dev.diegodc.moviesapp.core.model.Result
import kotlinx.coroutines.flow.*
import java.lang.Exception

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
)  = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Result.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Result.Success(it) }
        } catch (e: Exception) {
            query().map { Result.Error(e) }
        }
    } else {
        query().map { Result.Success(it) }
    }

    emitAll(flow)
}