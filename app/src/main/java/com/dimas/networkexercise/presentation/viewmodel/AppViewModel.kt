package com.dimas.networkexercise.presentation.viewmodel

import android.util.Log
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

class AppViewModel(private val repository: DiseaseRepository): ViewModel() {

    private val _disease = MutableStateFlow<UIState<List<MachineDisease>>>(Initiate())
    val disease: StateFlow<UIState<List<MachineDisease>>> = _disease

    private val storeAllData: MutableList<MachineDisease> = mutableListOf()

    private val _filteredDiseases = MutableStateFlow<List<MachineDisease>>(emptyList())
    val filteredDiseases: StateFlow<List<MachineDisease>> = _filteredDiseases

    private val _nextCode = MutableStateFlow<UIState<String>>(Initiate())
    val nextCode: StateFlow<UIState<String>> = _nextCode

    fun getDisease() {
        viewModelScope.launch(Dispatchers.Main) {
            _disease.value = Loading(true)
            storeAllData.clear()
            val process = async(Dispatchers.IO) {
                repository.getDiseases()
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _disease.value = Loading(false)
                    storeAllData.addAll(state.data)
                    _disease.value = Success(data = state.data)
                }
                is NetworkState.Error ->{
                    _disease.value = Loading(false)
                    _disease.value = Error(state.error.message.orEmpty())
                }
            }
        }
    }

    fun getNextCode(currentCode: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _nextCode.value = Loading(true)
            val process = async(Dispatchers.IO) {
                repository.getNextCode(currentCode)
            }
            when(val state = process.await()) {
                is NetworkState.Success -> {
                    _nextCode.value = Loading(false)
                    _nextCode.value = Success(data = state.data)
                }
                is NetworkState.Error ->{
                    _nextCode.value = Loading(false)
                    _nextCode.value = Error(state.error.message.orEmpty())
                }
            }
        }
    }

    fun filteredByCode(currentCode: String) {
        viewModelScope.launch {
            val filteredList = storeAllData.filter { it.code == currentCode }
            _filteredDiseases.value = filteredList
        }
    }
}