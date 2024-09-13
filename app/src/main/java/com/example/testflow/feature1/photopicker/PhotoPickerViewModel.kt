package com.example.testflow.feature1.photopicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.testflow.feature1.model.Image
import com.example.testflow.feature1.repository.PhotoPickerRepository
import com.example.testflow.feature1.repository.impl.PhotoPickerRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PhotoPickerViewModel(
    photoPickerRepository: PhotoPickerRepository
) : ViewModel() {

    var images = flow<List<Image>> {}.flowOn(Dispatchers.IO)

    init {
        viewModelScope.launch {
            images = photoPickerRepository.getImagesInFolder(null)
            delay(1000)
        }
    }


    companion object {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(PhotoPickerViewModel::class.java)) {
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    val photoPickerRepository =
                        PhotoPickerRepositoryImpl(application.applicationContext)
                    return PhotoPickerViewModel(photoPickerRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}