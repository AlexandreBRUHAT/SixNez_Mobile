package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.User
import com.sixnez.service.MyApi
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
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
        _user.value = User()
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    private suspend fun insert() {
        Log.i("Register","Starting API Call")
        //TODO Encodage ???
        val resp =
            MyApi.retrofitService.register(""+_user.value?.login,
                ""+user.value?.password)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        try {
                            Log.i("onResponse", ""+response.code())

                            if (response.code() != 200) {
                                _alert.value = "Cet identifiant n'est pas disponible"
                            } else {
                                _alert.value = "Vous avez bien été inscrit !"
                                _navigateToLoginFragment.value = true
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

    //end register
    private val _navigateToLoginFragment = MutableLiveData<Boolean>()

    val navigateToLoginFragment: LiveData<Boolean>
        get() = _navigateToLoginFragment


    fun onValidateAccount() {
//        _navigateToLoginFragment.value = true

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

            if(!user.password.equals(verifPassword)) {
                _alert.value = "Les mots de passe ne correspondent pas"
                return@launch
            }

            insert()
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