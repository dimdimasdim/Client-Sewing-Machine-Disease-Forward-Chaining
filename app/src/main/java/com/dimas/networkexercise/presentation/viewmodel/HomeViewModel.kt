package com.dimas.networkexercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.networkexercise.domain.DiseaseRepository
import com.dimas.networkexercise.domain.model.MachineDisease
import com.dimas.networkexercise.utils.Error
import com.dimas.networkexercise.utils.Initiate
import com.dimas.networkexercise.utils.Loading
import com.dimas.networkexercise.utils.NetworkState
import com.dimas.networkexercise.utils.Success
import com.dimas.networkexercise.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DiseaseRepository): ViewModel() {

    private val _movie = MutableStateFlow<UIState<List<MachineDisease>>>(Initiate())
    val movie: StateFlow<UIState<List<MachineDisease>>> = _movie

    private val storeAllData: MutableList<MachineDisease> = mutableListOf()

    private val _filteredDiseases = MutableStateFlow<List<MachineDisease>>(emptyList())
    val filteredDiseases: StateFlow<List<MachineDisease>> = _filteredDiseases

    fun getDisease() {
        viewModelScope.launch(Dispatchers.Main) {
            _movie.value = Loading(true)
            storeAllData.clear()
            val process = async(Dispatchers.IO) {
                repository.getDiseases()
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _movie.value = Loading(false)
                    storeAllData.addAll(state.data)
                    _movie.value = Success(data = state.data)
                }
                is NetworkState.Error ->{
                    _movie.value = Loading(false)
                    _movie.value = Error(state.error.message.orEmpty())
                }
            }
        }
    }

    fun filteredByCode(code: String) {
        viewModelScope.launch {
            val filteredList = storeAllData.filter { it.code == code }
            _filteredDiseases.value = filteredList
        }
    }
}