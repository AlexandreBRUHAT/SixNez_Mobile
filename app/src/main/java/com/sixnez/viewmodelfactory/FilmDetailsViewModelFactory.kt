package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.model.FilmDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.viewmodel.FilmDetailsViewModel

class FilmDetailsViewModelFactory (
    private val application: Application,
    private val film: FilmDetailledDTO
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmDetailsViewModel::class.java)) {
            return FilmDetailsViewModel(application, film) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}