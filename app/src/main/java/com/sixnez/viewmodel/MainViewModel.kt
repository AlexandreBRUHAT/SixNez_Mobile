package com.sixnez.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User
import com.sixnez.service.MyApi
import com.sixnez.service.getToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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
        Log.i("Test","Connected")
        _user.value = userCo
        _isConnected.value = true
    }

    fun disconnect() {
        Log.i("Test","Disconnected")
        _user.value = null
        _isConnected.value = false
    }
}