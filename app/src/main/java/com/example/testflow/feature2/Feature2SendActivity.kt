package com.example.testflow.feature2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testflow.R

class Feature2SendActivity : AppCompatActivity() {
    private lateinit var viewModel: Feature2ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_2)
        viewModel = Feature2ViewModel.instance

        val button = findViewById<Button>(R.id.next)
        for (i in 1..10) {
            viewModel.sendItem(i)
        }
        button.setOnClickListener {
            val intent = Intent(this, Feature2ReceiveActivity::class.java)
            startActivity(intent)
        }
    }
}