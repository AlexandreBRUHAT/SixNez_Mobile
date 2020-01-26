package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel


class HomeViewModel(
    application: Application
) : AndroidViewModel(application)
{
    init {
        Log.i("HomeViewModel", "created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "destroyed")
    }
}