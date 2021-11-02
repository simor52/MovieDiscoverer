package com.example.yassirtest.di

import android.app.Application
import androidx.room.Room
import com.example.yassirtest.persistence.AppDatabase
import com.example.yassirtest.persistence.GenreIdsConverter
import com.example.yassirtest.persistence.GenreListConverter
import com.example.yassirtest.persistence.MovieDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
        genreIdsConverter: GenreIdsConverter,
        genreListConverter: GenreListConverter,
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "movie.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(genreIdsConverter)
            .addTypeConverter(genreListConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideTypeGenreIdsConverter(moshi: Moshi): GenreIdsConverter {
        return GenreIdsConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideTypeGenreListConverter(moshi: Moshi): GenreListConverter {
        return GenreListConverter(moshi)
    }
}