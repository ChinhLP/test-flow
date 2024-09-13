package com.example.testflow.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Feature2ViewModel() : ViewModel() {

    companion object {
        private val _flow2 = MutableStateFlow<MutableList<Int>>(mutableListOf())
        var flow2 = _flow2.asStateFlow()
    }


    fun sendItem(listItem: MutableList<Int>) {
        viewModelScope.launch {
            _flow2.value = listItem
        }
    }
}

