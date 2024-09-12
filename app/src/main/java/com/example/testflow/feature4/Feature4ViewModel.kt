package com.example.testflow.feature4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Feature4ViewModel : ViewModel() {
    private val _flow1 = MutableStateFlow<Int?>(null)
    var flow1 = _flow1.asStateFlow()
    private val _flow2 = MutableStateFlow<Int?>(null)
    var flow2 = _flow2.asStateFlow()

    init {
        loop(_flow1 , 1000)
        loop(_flow2,3000)
    }

    private fun loop ( flow: MutableStateFlow<Int?> , timeDelay: Long)  {
        viewModelScope.launch {
            for (i in 0 until 10) {
                delay(timeDelay)
                flow.value = i
            }
        }
    }
}