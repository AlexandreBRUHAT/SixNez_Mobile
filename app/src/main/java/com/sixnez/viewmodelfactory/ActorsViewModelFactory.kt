package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.viewmodel.ActorsViewModel

class ActorsViewModelFactory (
    private val application: Application,
    private val userID: Long = 0L
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActorsViewModel::class.java)) {
            return ActorsViewModel(application,userID) as T // userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}