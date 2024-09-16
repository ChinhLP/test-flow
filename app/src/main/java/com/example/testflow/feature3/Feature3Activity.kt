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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class Feature3Activity : AppCompatActivity() {
    private val viewModel: Feature3ViewModel by viewModels()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        task345()
        task125()

    }

    private fun task125() {
        viewModel.flow3
            .filterNotNull() // chỉ chạy khi value not null
            .distinctUntilChanged() // chỉ chạy khi value thay đổi
            .transform {
                // thiết lập giá trị trước khi emit
                delay(1000)
                emit(it)
            }
            .onEach {
                // thực hiện action mà không thay đổi đến giá trị
                Log.d("ChatActivity", "$it")
            }
            .take(8) // chỉ lấy 8  giá trị emit
            .onCompletion {
                // thực hiện đóng job khi flow kết thúc
                viewModel.job?.cancel()
            }
            .flowOn(Dispatchers.IO) // gán luồng flow lên IO
            .flowWithLifecycle(lifecycle) // theo dõi trạng thái lifecycle
            .launchIn(lifecycleScope) // thu thập giá trị trả ra khai báo thay collect
    }
    private fun task345() {
        viewModel.flow3
            .filterNotNull()
            .filter {
                // chỉ lấy giá trị lẻ
                it % 2 != 0
            }
            .onEach {
                Log.d("ddd", "$it")
            }
            .flowOn(Dispatchers.IO)
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}