package com.example.yassirtest.di

import com.example.yassirtest.network.TheMovieDbClient
import com.example.yassirtest.persistence.AppDatabase
import com.example.yassirtest.persistence.MovieDao
import com.example.yassirtest.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        theMovieDbClient: TheMovieDbClient,
        movieDb: AppDatabase,
        coroutineDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepository(theMovieDbClient, movieDb, coroutineDispatcher)
    }
}