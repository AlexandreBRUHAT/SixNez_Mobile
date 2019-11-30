package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User
import kotlinx.coroutines.*
import java.security.MessageDigest


class ActorsViewModel(
    application: Application,
    private val userID: Long = 0L // userID
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        Log.i("ActorsViewModel", "created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ActorsViewModel", "destroyed")
        viewModelJob.cancel()
    }
}