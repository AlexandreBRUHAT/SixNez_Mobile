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

class MainViewModel {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var genres : List<String> = ArrayList<String>()

    init {
        _user.value = null
        _isConnected.value = false
    }

    fun getGenres(): List<String> {
        if (genres.isEmpty()) {
            var getGenres = MyApi.retrofitService.getGenres(
                "Bearer " + getToken()
            )

            coroutineScope.launch {
                try {
                    Log.i("getGenres", "started")
                    var result = getGenres.await()

                    genres = result

                    Log.i("getGenres", "Succès : " + result.size + " genres récupérés")
                } catch (e: Exception) {
                    Log.i("Echec", e.message)
                }
            }
        }

        return genres
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