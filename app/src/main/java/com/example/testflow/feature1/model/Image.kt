package com.example.testflow.feature1.model

import android.net.Uri


data class Image(
    val id: Long,
    val uri: Uri,
    val path: String
)