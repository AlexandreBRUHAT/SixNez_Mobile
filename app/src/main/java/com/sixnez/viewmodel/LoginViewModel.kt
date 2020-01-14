package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User
import kotlinx.coroutines.*
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

    init {
        Log.i("LoginViewModel", "created")
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = ""
    }

    //log me in
    private val _login = MutableLiveData<Boolean>()

    val login: LiveData<Boolean>
        get() = _login


    fun onValidateLogin() {

        _login.value = true

//        uiScope.launch {
//            var user = user.value ?: return@launch
//
//            if(user.login.isNullOrEmpty()) {
//                _alert.value = "Veuillez entrer un nom de compte"
//                return@launch
//            }
//
//            if(user.password.isNullOrEmpty()) {
//                _alert.value = "Veuillez entrer un mot de passe"
//                return@launch
//            }
//            if (login()) {
//                _login.value = true
//            }
//            else {
//                _alert.value = "Connexion échouée"
//                return@launch
//            }
//        }
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

    private suspend fun login(): Boolean { // TODO appel au webservice
        withContext(Dispatchers.IO) {
            //
        }
        return true
    }
    
    fun encode(type:String, input: String): String { // TODO look encodage sur la partie Vue
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "destroyed")
        viewModelJob.cancel()
    }
}