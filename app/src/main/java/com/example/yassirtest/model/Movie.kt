package com.example.yassirtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Movie (
    var page : Int = 0,
    @field:Json(name = "id") @PrimaryKey val id : Int,
    @field:Json(name = "genre_ids") val genreIds : List<Int>,
    @field:Json(name = "overview") val overview : String,
    @field:Json(name = "popularity") val popularity : Double,
    @field:Json(name = "poster_path") val posterPath : String,
    @field:Json(name = "release_date") val releaseDate : String,
    @field:Json(name = "title") val title : String,
    @field:Json(name = "video") val video : Boolean,
    @field:Json(name = "vote_average") val voteAverage : Double,
    @field:Json(name = "vote_count") val voteCount : Int
)