package com.sixnez.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User

class MainViewModel {
    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        _user.value = null
        _isConnected.value = false
    }

    fun connect(userCo: User?) {
        _user.value = userCo
        _isConnected.value = true
    }

    fun disconnect() {
        //_user.value = null
        _isConnected.value = true
    }
}