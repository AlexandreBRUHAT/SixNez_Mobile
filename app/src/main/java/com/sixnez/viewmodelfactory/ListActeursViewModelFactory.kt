package com.sixnez.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.model.ActeurRequest
import com.sixnez.viewmodel.ListActeursViewModel


class ListActeursViewModelFactory (
    private val request: ActeurRequest
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListActeursViewModel::class.java)) {
            return ListActeursViewModel(request) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}