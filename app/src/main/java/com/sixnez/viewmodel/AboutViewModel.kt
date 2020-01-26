package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel


class AboutViewModel(
    application: Application
) : AndroidViewModel(application)
{
    init {
        Log.i("AboutViewModel", "created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("AboutViewModel", "destroyed")
    }
}