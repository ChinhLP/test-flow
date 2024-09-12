package com.example.testflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*state flow:
    + là 1 hot flow.
    + chạy bất đồng bộ có thể kết hợp với các coroutine.
    + state flow thì chỉ phát 1 giá trị và luôn phát ra giá trị hiện tại khi có 1 collection mới.
    + state flow bắt buộc có giá trị ban đầu.
*/
fun main(): Unit = runBlocking {
    val stateFlow = MutableStateFlow(0) // Bắt đầu với giá trị mặc định là 0

    // Coroutine phát giá trị mới sau mỗi giây
    launch {
        repeat(5) {
            delay(1000)
            stateFlow.value = it // Cập nhật giá trị hiện tại
            println("StateFlow emitted: $it")
        }
    }

    // Coroutine thu từ StateFlow
    launch {
        delay(2500) // Bắt đầu thu sau 2.5 giây
        stateFlow.collect { value ->
            println("Collector received from StateFlow: $value")
        }
    }
}