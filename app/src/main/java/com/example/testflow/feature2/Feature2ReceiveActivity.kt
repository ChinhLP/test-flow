package com.example.testflow.feature2

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testflow.R
import com.example.testflow.feature2.Feature2ViewModel.Companion.flow2
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class Feature2ReceiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val listView = findViewById<ListView>(R.id.listView)
        flow2
            .onEach { list ->
                listView.adapter =
                    ArrayAdapter(
                        this,
                        R.layout.layout_item_pick_photo,
                        R.id.text,
                        list.map { it.toString() })
            }
            .distinctUntilChanged()
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}