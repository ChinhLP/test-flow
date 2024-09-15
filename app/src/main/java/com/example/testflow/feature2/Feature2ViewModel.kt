package com.example.testflow.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class Feature2ViewModel() : ViewModel() {


    private val _flow2 = MutableSharedFlow<Int>(replay = 10)
    var flow2 = _flow2.asSharedFlow()


    fun sendItem(item: Int) {
        viewModelScope.launch {
            _flow2.emit(item)
        }
    }

    companion object ViewModelSingleton {
        val instance: Feature2ViewModel by lazy {
            Feature2ViewModel()
        }

    }
}

