package com.example.yassirtest.persistence

import androidx.paging.PagingSource
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

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    fun getMovieList(): PagingSource<Int, Movie>

    @Query("DELETE FROM Movie")
    suspend fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieInfo(movieInfo: MovieInfo)

    @Query("SELECT * FROM MovieInfo WHERE id = :id")
    suspend fun getMovieInfo(id: Long): MovieInfo?
}