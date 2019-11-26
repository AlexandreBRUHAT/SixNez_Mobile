package com.sixnez.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sixnez.database.UserDAO
import com.sixnez.viewmodel.RegisterViewModel

class RegisterViewModelFactory (
    private val dataSource: UserDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(dataSource, application) as T // userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}