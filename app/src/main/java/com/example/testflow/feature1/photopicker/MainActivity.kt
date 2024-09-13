package com.example.testflow.feature1.photopicker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testflow.R
import com.example.testflow.feature2.Feature2SendActivity
import com.example.testflow.feature3.Feature3Activity
import com.example.testflow.feature4.Feature4Activity

class MainActivity : AppCompatActivity() {

    private val requestPermissionImage: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            if (permission.values.all { it }) {
                openPickerActivity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.btnFeature1).setOnClickListener {
            checkPermissionHandler(this@MainActivity) {
                openPickerActivity()
            }
        }
        findViewById<Button>(R.id.btnFeature2).setOnClickListener {
            openFeature2Activity()

        }
        findViewById<Button>(R.id.btnFeature3).setOnClickListener {
            openFeature3Activity()

        }
        findViewById<Button>(R.id.btnFeature4).setOnClickListener {
            openFeature4Activity()
        }
    }

    private fun openPickerActivity() {
        startActivity(Intent(this, PhotoPickerActivity::class.java))
    }

    private fun openFeature2Activity() {
        startActivity(Intent(this, Feature2SendActivity::class.java))
    }

    private fun openFeature3Activity() {
        startActivity(Intent(this, Feature3Activity::class.java))
    }

    private fun openFeature4Activity() {
        startActivity(Intent(this, Feature4Activity::class.java))
    }

    private fun checkPermissionHandler(
        context: Context,
        onGrantedPermission: () -> Unit,
    ) {
        if (isReadImagePermissionGranted(context)) {
            onGrantedPermission()
        } else {
            val permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            requestPermissionImage.launch(permissions)
        }
    }

    private fun isReadImagePermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}