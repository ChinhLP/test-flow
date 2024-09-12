package com.example.testflow.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class Feature2ViewModel : ViewModel() {
    private val _flow2 = MutableSharedFlow<MutableList<Int>>()
    var flow2 = _flow2.asSharedFlow()

    fun sendItem(listItem: MutableList<Int>) {
        viewModelScope.launch {
            _flow2.emit(listItem)
        }
    }
}