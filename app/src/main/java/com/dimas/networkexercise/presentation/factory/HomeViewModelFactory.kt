package com.dimas.networkexercise.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimas.networkexercise.domain.DiseaseRepository
import com.dimas.networkexercise.presentation.viewmodel.HomeViewModel

class HomeViewModelFactory(
    private val repository: DiseaseRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}