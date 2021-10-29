package com.example.yassirtest.di

import android.app.Application
import androidx.room.Room
import com.example.yassirtest.persistence.AppDatabase
import com.example.yassirtest.persistence.GenreIdsConverter
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
        genreIdsConverter: GenreIdsConverter
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "Pokedex.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(genreIdsConverter)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideTypeResponseConverter(moshi: Moshi): GenreIdsConverter {
        return GenreIdsConverter(moshi)
    }
}