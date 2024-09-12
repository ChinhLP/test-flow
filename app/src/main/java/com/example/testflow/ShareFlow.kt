package com.example.testflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
share flow
  + là 1 hot flow.
  + chạy bất đồng bộ có thể kết hợp với các coroutine.
  + share flow là 1 dòng có thể phát ra nhiều giá trị và có thể không lưu giá trị sau khi phát.
  + share flow không cần giá trị ban đầu.
  + share flow có thể sử dụng replay để lưu lại các giá trị emit trước đó để phát lại cho các colection mới.
- Khác nhau:
  + share flow là 1 dòng có thể phát ra nhiều giá trị và có thể không lưu giá trị sau khi phát , còn state flow thì chỉ phát 1 giá trị và luôn phát ra giá trị hiện tại khi có 1 collection mới.
  + share flow không cần giá trị ban đầu còn state flow bắt buộc có giá trị ban đầu.
  + share flow có thể sử dụng replay để lưu lại các giá trị emit trước đó để phát lại cho các colection mới còn state flow thì luôn lưu lại giá trị hiện tại.
*/

fun main() = runBlocking {

    /*share flow sử dụng replay là 2 để lưu 2 giá trị được emit trước đó , không cần giá trị ban đầu */
    val sharedFlow = MutableSharedFlow<Int>(replay = 2)

    val job1 = launch {
        sharedFlow.collect { value ->
            println("Collector 1: $value")
        }
    }

    delay(1000)
    sharedFlow.emit(1)
    delay(1000)
    sharedFlow.emit(2)
    /* nếu giá replay = 2 thì sẽ in ra 2 giá trị 1 và 2 ,3 */
    /* nếu giá replay = 0 thì sẽ không in ra được 2 giá trị 1 và 2 mà bắt đầu in từ giá trị 3 */
    val job2 = launch {
        sharedFlow.collect { value ->
            println("Collector 2: $value")
        }
    }
    delay(1000)
    sharedFlow.emit(3)
    delay(1000)

    /*
     giá trị in ra khi replay = 2:
      Collector 1: 1
      Collector 1: 2
      Collector 2: 1
      Collector 2: 2
      Collector 1: 3
      Collector 2: 3
*/

    /*
    giá trị in ra khi replay = 0:
     Collector 1: 1
     Collector 1: 2
     Collector 2: 3
     Collector 1: 3*/

    job1.cancel()
    job2.cancel()
}
