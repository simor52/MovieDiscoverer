package com.example.yassirtest.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yassirtest.model.Movie
import com.example.yassirtest.model.MovieInfo

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<Movie>)

    @Query("SELECT * FROM Movie WHERE page = :page_ ORDER BY popularity DESC")
    suspend fun getMovieList(page_: Int): List<Movie>

    @Query("SELECT * FROM Movie WHERE page <= :page_ ORDER BY popularity DESC")
    suspend fun getAllMovieList(page_: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieInfo(movieInfo: MovieInfo)

    @Query("SELECT * FROM MovieInfo WHERE id = :id")
    suspend fun getMovieInfo(id: Long): MovieInfo?
}