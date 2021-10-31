package com.example.yassirtest.ui.main

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.example.yassirtest.model.Movie
import com.example.yassirtest.repository.MovieRepository
import com.example.yassirtest.ui.BaseViewModel
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MovieRepository
) : BaseViewModel() {


    private val movieFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(1)
    private val movieListFlow = movieFetchingIndex.flatMapLatest { page ->
        mainRepository.fetchMovieList(
            page = page,
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = { toastMessage = it }
        )
    }

    @get:Bindable
    val movieList: List<Movie> by movieListFlow.asBindingProperty(viewModelScope, emptyList())

    init {
        Timber.d("init MainViewModel")
    }

    @MainThread
    fun fetchNextmovieList() {
        if (!isLoading) {
            movieFetchingIndex.value++
        }
    }

    @MainThread
    override fun refresh() {
        if (!isLoading) {
            movieFetchingIndex.value = 1
        }
    }
}
