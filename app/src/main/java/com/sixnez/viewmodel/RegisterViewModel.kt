package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User
import kotlinx.coroutines.*
import java.security.MessageDigest


class RegisterViewModel(
    application: Application
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _verifPassword: String? = ""
    var verifPassword: String?
        get() = _verifPassword
        set(value) {
            _verifPassword = value
        }

    init {
        Log.i("RegisterViewModel", "created")
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = ""
    }

    private suspend fun insert(): Boolean { // TODO appel au webservice
        withContext(Dispatchers.IO) {
            //
        }
        return true
    }

    //end register
    private val _navigateToLoginFragment = MutableLiveData<Boolean>()

    val navigateToLoginFragment: LiveData<Boolean>
        get() = _navigateToLoginFragment


    fun onValidateAccount() {
        _navigateToLoginFragment.value = true

        uiScope.launch {
            val user = user.value ?: return@launch

            if(user.login.isNullOrEmpty()){
                _alert.value = "Veuillez entrer un nom de compte"
                return@launch
            }

            if(user.password.isNullOrEmpty()) {
                _alert.value = "Veuillez entrer un mot de passe"
                return@launch
            }

            if(user.password != verifPassword) {
                _alert.value = "Les mots de passe ne correspondent pas"
                return@launch
            }

            user.password = encode("SHA1",user.password+"")

            if (insert()) {
                _navigateToLoginFragment.value = true
            } else {
                _alert.value = "Échec de l'inscription"
            }
        }
    }

    fun encode(type:String, input: String): String {
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

    fun doneNavigating() {
        _navigateToLoginFragment.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterViewModel", "destroyed")
        viewModelJob.cancel()
    }
}