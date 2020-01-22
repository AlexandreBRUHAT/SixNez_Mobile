package com.sixnez.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.model.FilmRequest
import com.sixnez.viewmodel.ListFilmsViewModel


class ListFilmsViewModelFactory (
    private val request: FilmRequest
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListFilmsViewModel::class.java)) {
            return ListFilmsViewModel(request) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}