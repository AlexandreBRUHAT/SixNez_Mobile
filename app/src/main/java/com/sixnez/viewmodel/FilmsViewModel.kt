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