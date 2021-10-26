package com.example.yassirtest.network

import com.example.yassirtest.BuildConfig
import com.example.yassirtest.model.Movie
import com.example.yassirtest.model.MovieResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class TheMovieDbClient @Inject constructor(
    private val theMovieDbService: TheMovieDbService
) {
    suspend fun fetchMovieList(page: Int = 1) = theMovieDbService.fetchMovieList(BuildConfig.APIKEY, page)

    suspend fun fetchMovieInfo(id: Long) = theMovieDbService.fetchMovieInfo(BuildConfig.APIKEY, id)
}