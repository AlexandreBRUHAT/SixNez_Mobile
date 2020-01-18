package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User
import com.sixnez.service.MyApi
import kotlinx.coroutines.*
import java.lang.Exception
import java.security.MessageDigest


class LoginViewModel(
    application: Application
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    init {
        Log.i("LoginViewModel", "created")
        _user.value = User()
    }


    fun doneAlerting() {
        _alert.value = null
    }

    //log me in
    private val _login = MutableLiveData<Boolean>()

    val login: LiveData<Boolean>
        get() = _login


    fun onValidateLogin() {

        uiScope.launch {
            var user = user.value ?: return@launch

            if(user.login.isNullOrEmpty()) {
                _alert.value = "Veuillez entrer un nom de compte"
                return@launch
            }

            if(user.password.isNullOrEmpty()) {
                _alert.value = "Veuillez entrer un mot de passe"
                return@launch
            }
            if (login()) {
                _login.value = true
            }
            else {
                _alert.value = "Connexion échouée"
                return@launch
            }
        }
    }


    //register
    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister


    fun onCreateAccount() {
        _navigateToRegister.value = true
    }

    fun doneNavigating() {
        _login.value = null
        _navigateToRegister.value = false
    }

    private suspend fun login(): Boolean {
        Log.i("Login","Starting API Call")
        val response = MyApi.retrofitService.login(""+_user.value?.login, ""+user.value?.password)

        try {
            _alert.value = response.await()
        } catch (e: Exception) {
            _alert.value = "L'identifiant et le mot de passe ne correspondent pas"
            return false
        }

        return true
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "destroyed")
        viewModelJob.cancel()
    }
}