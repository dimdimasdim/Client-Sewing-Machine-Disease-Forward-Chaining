package com.dimas.networkexercise.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimas.networkexercise.domain.DiseaseRepository
import com.dimas.networkexercise.presentation.viewmodel.AppViewModel

class HomeViewModelFactory(
    private val repository: DiseaseRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            AppViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}