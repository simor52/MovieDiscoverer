package com.example.yassirtest.repository

import androidx.annotation.WorkerThread
import androidx.paging.*
import com.example.yassirtest.model.ErrorResponseMapper
import com.example.yassirtest.model.Movie
import com.example.yassirtest.model.MovieInfo
import com.example.yassirtest.network.TheMovieDbClient
import com.example.yassirtest.persistence.AppDatabase
import com.example.yassirtest.persistence.MovieDao
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieClient: TheMovieDbClient,
    private val movieDb: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @ExperimentalPagingApi
    @WorkerThread
    fun fetchMovieList() = Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = MovieRemoteMediator(
                movieClient,
                movieDb
            ),
            pagingSourceFactory = { movieDb.movieDao().getMovieList() }
        ).flow

    @WorkerThread
    fun fetchMovieInfo(
        id: Long,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow<MovieInfo?> {
        val movieInfo = movieDb.movieDao().getMovieInfo(id)
        if (movieInfo == null) {
            /**
             * fetches a [MovieInfo] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#suspendonsuccess-suspendonerror-suspendonexception)
             */
            val response = movieClient.fetchMovieInfo(id)
            response.suspendOnSuccess {
                movieDb.movieDao().insertMovieInfo(data)
                emit(data)
            }
                // handles the case when the API request gets an error response.
                // e.g., internal server error.
                .onError {
                    /** maps the [ApiResponse.Failure.Error] to the [MovieErrorResponse] using the mapper. */
                    map(ErrorResponseMapper) { onError("[Code: $code]: $message") }
                }
                // handles the case when the API request gets an exception response.
                // e.g., network connection error.
                .onException { onError(message) }
        } else {
            emit(movieInfo)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}

