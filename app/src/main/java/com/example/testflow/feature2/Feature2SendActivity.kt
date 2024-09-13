package com.example.testflow.feature2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testflow.R

class Feature2SendActivity : AppCompatActivity() {
    private val viewmodel: Feature2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_2)
        val button = findViewById<Button>(R.id.next)
        val listItem = mutableListOf<Int>()
        for (i in 1..10) {
            listItem.add(i)
        }
        viewmodel.sendItem(listItem)
        button.setOnClickListener {
            val intent = Intent(this, Feature2ReceiveActivity::class.java)
            startActivity(intent)
        }
    }
}