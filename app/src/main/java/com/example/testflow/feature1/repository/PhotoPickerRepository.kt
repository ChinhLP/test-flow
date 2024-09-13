package com.example.testflow.feature1.repository

import androidx.annotation.WorkerThread
import com.example.testflow.feature1.model.Image
import kotlinx.coroutines.flow.Flow


interface PhotoPickerRepository {
    @WorkerThread
    fun getImagesInFolder(bucketId: Int?, emitItemCount: Int = 50): Flow<List<Image>>
}