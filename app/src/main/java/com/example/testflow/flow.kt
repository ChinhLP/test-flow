package com.example.testflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/* flow chia ra hot flow và cold flow */
fun main() = runBlocking {
    /* đây là luồng cold flow  */
    val flow = flow {
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }
        println("run flow")
    }.stateIn(scope = this, started = kotlinx.coroutines.flow.SharingStarted.Lazily, 0)

    /* vì đây là luồng cold flow nên khi collect thì flow sẽ chạy lại từ đầu và là các dòng riêng biệt */

    val job1 = launch {
        flow.collect { it ->
            println("job1: $it")
        }
    }

    val job2 = launch {
        flow.collect { it ->
            println("job2:  $it")
        }
    }

    /*giá trị dầu ra : run flow
      job1: 1
      job2:  1
      job1: 2
      job2:  2
      job1: 3
      job2:  3
      job1: 4
      job2:  4
      job1: 5
      run flow
      job2:  5
      run flow
*/


}