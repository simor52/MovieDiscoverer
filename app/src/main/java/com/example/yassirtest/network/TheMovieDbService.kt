package com.example.yassirtest.network

import com.example.yassirtest.model.MovieInfo
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
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): ApiResponse<MovieInfo>
}