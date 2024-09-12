package com.example.testflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/*
share flow và state flow :
- Giống nhau :
  + đều tuân theo flow để thu và phát dữ liệu .
  + đều chạy bất đồng bộ có thể kết hợp với các coroutine
  + cả 2 có thể thu thập đồng thời ở nhiều coroutine
  + cả 2 đều là các dòng không chặn , không yêu cầu đồng bộ hóa giữa các coroutine.
- Khác nhau:
  + share flow là 1 dòng có thể phát ra nhiều giá trị và có thể không lưu giá trị sau khi phát , còn state flow thì chỉ phát 1 giá trị và luôn phát ra giá trị hiện tại khi có 1 collection mới.
  + share flow không cần giá trị ban đầu còn state flow bắt buộc có giá trị ban đầu.
  + share flow có thể sử dụng replay để lưu lại các giá trị emit trước đó để phát lại cho các colection mới còn state flow thì luôn lưu lại giá trị hiện tại.
*/

fun main() = runBlocking {

    /*share flow sử dụng replay là 2 để lưu 2 giá trị được emit trước đó , không cần giá trị ban đầu */
    val sharedFlow = MutableSharedFlow<Int>(replay = 0)
    /* state flow cần có giá trị ban đầu là 0 */
    val stateFlow = MutableStateFlow<Int>(0)

    /* flow có thể gán threat cho flow */
    val flow = flow<Int> {
        for (i in 1..3) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.IO)

    val job1 = launch {
        sharedFlow.collect { value ->
            println("Collector 1: $value")
        }
    }

    delay(1000)
    sharedFlow.emit(1)
    delay(1000)
    sharedFlow.emit(2)
    delay(1000)
    /* nếu giá replay = 2 thì sẽ in ra 2 giá trị 1 và 2 ,3 */
    /* nếu giá replay = 0 thì sẽ không in ra được 2 giá trị 1 và 2 mà bắt đầu in từ giá trị 3 */
    val job2 = launch {
        sharedFlow.collect { value ->
            println("Collector 2: $value")
        }
    }
    sharedFlow.emit(3)
    delay(1000)

    job1.cancel()
    job2.cancel()
}
