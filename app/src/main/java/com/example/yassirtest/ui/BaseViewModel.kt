package com.example.yassirtest.ui

import androidx.databinding.Bindable
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty

abstract class BaseViewModel: BindingViewModel() {
    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        protected set

    @get:Bindable
    var toastMessage: String? by bindingProperty(null)
        protected set

    abstract fun refresh()
}