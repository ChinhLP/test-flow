package com.example.testflow.feature4

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testflow.R
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip

class Feature4Activity : AppCompatActivity() {
    private val viewModel: Feature4ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        combineFlow() // in ra giá trị khi có 1 trong 2 flow emit
        zipFlow() // in ra giá trị khi 2 flow đều emit
    }

    private fun combineFlow() {
        // lệnh combine là kết hợp 2 flow lại với nhau và 2 flow sẽ không đợi nhau
        // in ra giá trị khi 1 trong 2 emit
        viewModel.flow1.combine(viewModel.flow2) { flow1, flow2 ->
            if (flow1 != null && flow2 != null) {
                println("flow1: $flow1, flow2: $flow2")
            }
        }
            .filterNotNull()
            .distinctUntilChanged()
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun zipFlow() {
        // lệnh zip là kết hợp 2 flow lại với nhau và 2 flow sẽ đợi nhau emit
        viewModel.flow1.zip(viewModel.flow2) { flow1, flow2 ->
            if (flow1 != null && flow2 != null) {
                println("flow1: $flow1, flow2: $flow2")
            }
        }
            .filterNotNull()
            .distinctUntilChanged()
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}