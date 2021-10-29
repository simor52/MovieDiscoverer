package com.example.yassirtest.repository

import androidx.annotation.WorkerThread
import com.example.yassirtest.model.ErrorResponseMapper
import com.example.yassirtest.model.MovieInfo
import com.example.yassirtest.network.TheMovieDbClient
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
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieClient: TheMovieDbClient,
    private val movieDao: MovieDao,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun fetchMovieList(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        var movies = movieDao.getMovieList(page)
        if (movies.isEmpty()) {
            /**
             * fetches a list of [Movie] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#suspendonsuccess-suspendonerror-suspendonexception)
             */
            val response = movieClient.fetchMovieList(page = page)
            response.suspendOnSuccess {
                movies = data.results
                movies.forEach { movie -> movie.page = page }
                movieDao.insertMovieList(movies)
                emit(movieDao.getAllMovieList(page))
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
            emit(movieDao.getAllMovieList(page))
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)


    @WorkerThread
    fun fetchMovieInfo(
        id: Long,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow<MovieInfo?> {
        val movieInfo = movieDao.getMovieInfo(id)
        if (movieInfo == null) {
            /**
             * fetches a [MovieInfo] from the network and getting [ApiResponse] asynchronously.
             * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#suspendonsuccess-suspendonerror-suspendonexception)
             */
            val response = movieClient.fetchMovieInfo(id)
            response.suspendOnSuccess {
                movieDao.insertMovieInfo(data)
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
    }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}

