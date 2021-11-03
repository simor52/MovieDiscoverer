package com.example.yassirtest.network

import com.example.yassirtest.BuildConfig
import javax.inject.Inject

class TheMovieDbClient @Inject constructor(
    private val theMovieDbService: TheMovieDbService
) {
    suspend fun fetchMovieList(page: Int = 1) = theMovieDbService.fetchMovieList(BuildConfig.APIKEY, page)

    suspend fun fetchMovieInfo(id: Long) = theMovieDbService.fetchMovieInfo(id, BuildConfig.APIKEY)
}