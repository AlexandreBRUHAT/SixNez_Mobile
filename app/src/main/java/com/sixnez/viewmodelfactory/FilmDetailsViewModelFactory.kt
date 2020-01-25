package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.model.FilmDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.model.FilmIdDTO
import com.sixnez.viewmodel.FilmDetailsViewModel

class FilmDetailsViewModelFactory (
    private val application: Application,
    private val film: FilmDetailledDTO,
    private val idDTO: FilmIdDTO
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmDetailsViewModel::class.java)) {
            return FilmDetailsViewModel(application, film, idDTO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}