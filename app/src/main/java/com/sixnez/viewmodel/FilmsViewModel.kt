package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.FilmRequest
import com.sixnez.model.User
import com.sixnez.service.MyApi
import com.sixnez.service.getToken
import kotlinx.coroutines.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class FilmsViewModel(
    application: Application
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _request = MutableLiveData<FilmRequest>()
    val request: LiveData<FilmRequest>
        get() = _request

    init {
        Log.i("FilmsViewModel", "created")
        _request.value = FilmRequest()
        getGenres()
    }

    //launch request
    private val _navigateToListFilmsFragment = MutableLiveData<FilmRequest>()
    val navigateToListFilmsFragment: LiveData<FilmRequest>
        get() = _navigateToListFilmsFragment

    fun onValidateSearch() {
        _navigateToListFilmsFragment.value = _request.value
    }

    fun doneNavigating() {
        _navigateToListFilmsFragment.value = null
    }

    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>>
        get() = _genres

    fun getGenres() {

        MyApi.retrofitService.getGenres("Bearer "+ getToken())
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    try {
                        Log.i("onResponse", ""+response.code())

                        if (response.code() != 200) {
                            Log.i("getGenres", "Error response")
                        } else {
                            var jsonArray = JSONArray(response.body()!!.string())
                            val strArr = ArrayList<String>(jsonArray.length())

                            for (i in 0 until jsonArray.length()) {
                                strArr.add(jsonArray.getString(i))
                            }
                            _genres.value = strArr
                            Log.i("getGenres",_genres.value.toString())
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

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }

    fun onGenreSelected(genre: String) {
       request.value?.genre = genre
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("FilmsViewModel", "destroyed")
        viewModelJob.cancel()
    }
}