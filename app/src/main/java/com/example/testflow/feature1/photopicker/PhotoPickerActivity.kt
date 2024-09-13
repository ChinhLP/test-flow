package com.example.testflow.feature1.photopicker

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.testflow.R
import com.example.testflow.databinding.ActivityPhotoPickerBinding
import com.example.testflow.feature1.adapter.PhotoAdapter
import com.example.testflow.feature1.model.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PhotoPickerActivity : AppCompatActivity() {

    private val viewModel: PhotoPickerViewModel by viewModels { PhotoPickerViewModel.factory }
    private val binding by lazy {
        ActivityPhotoPickerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapter = PhotoAdapter()
        binding.rvPhoto.adapter = adapter

        viewModel.images.onEach {
            delay(1000)
            adapter.addPhotos(it)
        }
            .distinctUntilChanged()
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

    }

}