package com.example.yassirtest.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse (
    @field:Json(name = "page") val page : Int,
    @field:Json(name = "results") val results : List<Movie>,
    @field:Json(name = "total_pages") val totalPages : Int,
    @field:Json(name = "total_results") val totalResults : Int
)