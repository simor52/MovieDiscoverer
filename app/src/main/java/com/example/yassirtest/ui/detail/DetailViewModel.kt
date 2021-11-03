package com.example.yassirtest.ui.detail

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yassirtest.model.MovieInfo
import com.example.yassirtest.repository.MovieRepository
import com.example.yassirtest.ui.BaseViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
    private val detailRepository: MovieRepository,
    @Assisted private val movieId: Long
) : BaseViewModel() {



    @get:Bindable
    var movieInfo: MovieInfo? by bindingProperty(null)

    init {
        refresh()
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
        viewModelScope.launch {
            detailRepository.fetchMovieInfo(
                movieId,
                onStart = { isLoading = true },
                onComplete = { isLoading = false },
                onError = { toastMessage = it }
            ).collect {
                movieInfo = it
            }
        }
    }
}