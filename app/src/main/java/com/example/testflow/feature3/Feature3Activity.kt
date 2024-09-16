package com.example.testflow.feature3

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testflow.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class Feature3Activity : AppCompatActivity() {
    private val viewModel: Feature3ViewModel by viewModels()
    private var job1: Job? = null
    private var job2: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        task345()
        task125()

    }

    private fun task125() {
        job2 = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.foo()
                .filterNotNull() // chỉ chạy khi value not null
                .distinctUntilChanged() // chỉ chạy khi value thay đổi
                .map {
                    // thiết lập giá trị trước khi emit
                    delay(1000)
                    it
                }
                .take(8) // chỉ lấy 8  giá trị emit
                .onCompletion {
                    // thực hiện đóng job khi flow kết thúc
                    job2?.cancel()
                }
                .collect {
                    // thực hiện action mà không thay đổi đến giá trị
                    Log.d("hhh", "$it")
                }

        }

    }

    private fun task345() {
        job1 = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.foo()
                .filterNotNull()
                .filter {
                    // chỉ lấy giá trị lẻ
                    it % 2 != 0
                }
                .collect {
                    Log.d("ChatActivity", "$it")
                }
        }
    }
}