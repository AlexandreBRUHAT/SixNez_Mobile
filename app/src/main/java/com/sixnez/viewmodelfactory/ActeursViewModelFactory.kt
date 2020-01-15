package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.viewmodel.ActeursViewModel

class ActeursViewModelFactory (
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActeursViewModel::class.java)) {
            return ActeursViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}