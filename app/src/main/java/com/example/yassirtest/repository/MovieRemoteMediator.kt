package com.example.yassirtest.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.yassirtest.model.Movie
import com.example.yassirtest.network.TheMovieDbClient
import com.example.yassirtest.persistence.AppDatabase
import com.example.yassirtest.persistence.RemoteKeys
import com.skydoves.sandwich.getOrThrow
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val movieClient: TheMovieDbClient,
    private val movieDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (loadType) {

            //when it's the first time we're loading data
            //or when PagingDataAdapter.refresh() is called
            LoadType.REFRESH -> 1
            //When we need to load data at the beginning of the currently loaded data set:
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            //When we need to load data at the end of the currently loaded data set:
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                if(remoteKeys == null)
                    MediatorResult.Success(endOfPaginationReached = false)
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)
//                val lastItem = state.lastItemOrNull()
//                if (lastItem == null) {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//
//                lastItem.page + 1
            }
        }

        try {
            val apiResponse = movieClient.fetchMovieList(page).getOrThrow()
            val movies = apiResponse.results
            val endOfPaginationReached = apiResponse.totalPages == page
            movieDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.remoteKeysDao().clearRemoteKeys()
                    movieDatabase.movieDao().clearMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    it.page = page
                    RemoteKeys(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                movieDatabase.movieDao().insertMovieList(movies)
                movieDatabase.remoteKeysDao().insertAll(keys)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                movieDatabase.remoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                movieDatabase.remoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                movieDatabase.remoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }


}