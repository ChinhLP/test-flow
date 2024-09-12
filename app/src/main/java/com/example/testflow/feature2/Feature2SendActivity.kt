package com.example.testflow.feature2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testflow.R

class Feature2SendActivity : AppCompatActivity() {
    val viewmodel : Feature2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



    }
}