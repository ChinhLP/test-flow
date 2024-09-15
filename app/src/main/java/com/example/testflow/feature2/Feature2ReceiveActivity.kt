package com.example.testflow.feature2

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testflow.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class Feature2ReceiveActivity : AppCompatActivity() {
    private lateinit var viewModel: Feature2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        viewModel = Feature2ViewModel.instance

        val listView = findViewById<ListView>(R.id.listView)
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter
        viewModel.flow2
            .onEach { item ->
                Log.d("ChatActivity", "$item")
                adapter.add(item.toString())
            }
            .distinctUntilChanged()
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}