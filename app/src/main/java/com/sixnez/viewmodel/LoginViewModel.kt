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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    private val _login = MutableLiveData<String>()

    val login: LiveData<String>
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

            login()

            return@launch
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

    private suspend fun login() {
        Log.i("Login","Starting API Call")
        //TODO Encodage ???
        val resp =
            MyApi.retrofitService.login(""+_user.value?.login,
                ""+user.value?.password)
                .enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    Log.i("onResponse", ""+response.code())

                    if (response.code() != 200) {
                        _alert.value = "L'identifiant et le mot de passe ne correspondent pas"
                    } else {
                        _login.value = response.body()!!.string()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
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