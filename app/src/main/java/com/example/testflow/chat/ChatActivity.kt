package com.example.testflow.chat

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testflow.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class ChatActivity : AppCompatActivity() {
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        viewModel.flow1.onEach {
            Log.d("ChatActivity", "$it" )
        }
            .take(8)
            .onCompletion {
                Log.d("ChatActivity", "onCompletion")
            }
            .flowOn(Dispatchers.IO)
            .flowWithLifecycle(lifecycle)
            .distinctUntilChanged()
            .launchIn(lifecycleScope)
    }
}