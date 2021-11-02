package com.example.yassirtest.ui.main

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.yassirtest.model.Movie
import com.example.yassirtest.repository.MovieRepository
import com.example.yassirtest.ui.BaseViewModel
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MovieRepository
) : BaseViewModel() {

    @ExperimentalPagingApi
    private val movieListFlow =  mainRepository.fetchMovieList()


    @ExperimentalPagingApi
    @get:Bindable
    val movieList: PagingData<Movie>? by movieListFlow.asBindingProperty(viewModelScope, null)

    init {
        Timber.d("init MainViewModel")
    }

    @MainThread
    override fun refresh() {

    }
}
