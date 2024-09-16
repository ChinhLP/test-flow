package com.example.testflow.feature3

import android.util.Log
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

    fun foo() = flow {
        repeat(100) {
            delay(100)
            emit(it)
        }
    }

}