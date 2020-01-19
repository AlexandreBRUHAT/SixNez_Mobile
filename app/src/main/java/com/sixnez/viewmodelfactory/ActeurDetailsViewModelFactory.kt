package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.model.ActeurDTO
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.viewmodel.ActeurDetailsViewModel

class ActeurDetailsViewModelFactory (
    private val application: Application,
    private val acteur: ActeurDetailledDTO
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActeurDetailsViewModel::class.java)) {
            return ActeurDetailsViewModel(application, acteur) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}