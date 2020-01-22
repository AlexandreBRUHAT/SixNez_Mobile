package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.viewmodel.FilmsViewModel

class FilmsViewModelFactory (
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmsViewModel::class.java)) {
            return FilmsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}