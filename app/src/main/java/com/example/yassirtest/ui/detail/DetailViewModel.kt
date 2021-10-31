package com.example.yassirtest.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yassirtest.model.MovieInfo
import com.example.yassirtest.repository.MovieRepository
import com.example.yassirtest.ui.BaseViewModel
import com.skydoves.bindables.asBindingProperty
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
    detailRepository: MovieRepository,
    @Assisted private val movieId: Long
) : BaseViewModel() {


    private val movieInfoFlow: Flow<MovieInfo?> = detailRepository.fetchMovieInfo(
        movieId,
        onComplete = { isLoading = false },
        onError = { toastMessage = it }
    )

    @get:Bindable
    val movieInfo: MovieInfo? by movieInfoFlow.asBindingProperty(viewModelScope, null)

    init {
        Timber.d("init DetailViewModel")
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(movieId: Long): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            movieId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId) as T
            }
        }
    }

    override fun refresh() {

    }
}