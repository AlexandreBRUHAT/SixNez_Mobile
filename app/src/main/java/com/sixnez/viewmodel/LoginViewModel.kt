package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.database.UserDAO
import com.sixnez.model.User
import kotlinx.coroutines.*
import java.security.MessageDigest


class LoginViewModel(
    val database: UserDAO,
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
        Log.i("LoginViewModel", "created")
    }

    //log me in
    private val _navigateToNextFragment = MutableLiveData<User>()

    val navigateToNextFragment: LiveData<User>
        get() = _navigateToNextFragment


    fun onValidateLogin() {
        _navigateToNextFragment.value = User(1, "1","12")
        /*
        uiScope.launch {
            var user = user.value ?: return@launch

            if(user.login.isNullOrEmpty()) {
                return@launch
            }

            if(user.password.isNullOrEmpty()) {
                return@launch
            }

            val id = testLogin()
            Log.i("ID",id.toString())
            if (id > 0) {
                _user.value?.id = id
                _navigateToNextFragment.value = user
            }
            else {
                return@launch
            }
        }

         */
    }

    //register
    private val _navigateToRegister = MutableLiveData<Boolean>()

    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister


    fun onCreateAccount() {
        _navigateToRegister.value = true
    }

    fun doneNavigating() {
        _navigateToNextFragment.value = null
        _navigateToRegister.value = false
    }

    private suspend fun testLogin(): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.testLogin(user.value?.login?:"",encode("SHA1",user.value?.password+""))
        }
        return id
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

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "destroyed")
        viewModelJob.cancel()
    }
}