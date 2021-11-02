package com.example.yassirtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class MovieInfo (
    @field:Json(name = "adult") var adult : Boolean,
    @field:Json(name = "backdrop_path") var backdropPath : String,
    @field:Json(name = "budget") var budget : Double,
    @field:Json(name = "genres") var genres : List<Genre>,
    @field:Json(name = "homepage") var homepage : String,
    @field:Json(name = "id") @PrimaryKey var id : Long,
    @field:Json(name = "imdb_id") var imdbId : String,
    @field:Json(name = "original_language") var originalLanguage : String,
    @field:Json(name = "original_title") var originalTitle : String,
    @field:Json(name = "overview") var overview : String,
    @field:Json(name = "popularity") var popularity : Double,
    @field:Json(name = "poster_path") var posterPath : String,
    @field:Json(name = "release_date") var releaseDate : String,
    @field:Json(name = "revenue") var revenue : Double,
    @field:Json(name = "runtime") var runtime : Int,
    @field:Json(name = "status") var status : String,
    @field:Json(name = "tagline") var tagline : String,
    @field:Json(name = "title") var title : String,
    @field:Json(name = "video") var video : Boolean,
    @field:Json(name = "vote_average") var voteAverage : Double,
    @field:Json(name = "vote_count") var voteCount : Int
) {
    fun getPosterUrl() : String = "https://image.tmdb.org/t/p/w500${posterPath}"

    fun getReleaseYear() : String {
        return if (releaseDate.isNullOrEmpty() || releaseDate.length < 4) ""
        else releaseDate.take(4)
    }
}