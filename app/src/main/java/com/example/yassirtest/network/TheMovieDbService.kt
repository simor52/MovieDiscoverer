package com.example.yassirtest.network

import com.example.yassirtest.model.Movie
import com.example.yassirtest.model.MovieResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("discover/movie")
    suspend fun fetchMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): ApiResponse<MovieResponse>

    @GET("movie/{id}")
    suspend fun fetchMovieInfo(
        @Query("api_key") apiKey: String,
        @Path("id") id: Long
    ): ApiResponse<Movie>
}