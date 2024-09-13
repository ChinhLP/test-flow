package com.example.testflow.feature3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class Feature3ViewModel() : ViewModel() {
    private val _flow3 = MutableStateFlow<Int?>(null)
    var flow3 = _flow3.asStateFlow()

    var job: Job? = null


    init {
        loop()
    }

//    fun foo() = flow {
//        repeat(5000) {
//            delay(500)
//            emit(it)
//        }
//    }

    private fun loop() {
        job = viewModelScope.launch {
            for (i in 0..1000) {
                delay(500)
                _flow3.value = i
            }
        }
    }
}