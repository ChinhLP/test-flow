package com.example.testflow.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ChatViewModel() : ViewModel() {
    private val _flow1 = MutableSharedFlow<Int>(replay = 8)
    var flow1 = _flow1.asSharedFlow()

    var job: Job? = null



    init {
        queryPhoto()
    }

    private fun queryPhoto() {
        job = viewModelScope.launch(Dispatchers.IO) {
            for (i in 0..1000) {
                delay(1000)
                _flow1.emit(i)
            }
        }
    }

}