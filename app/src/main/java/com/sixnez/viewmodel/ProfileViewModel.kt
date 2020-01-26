package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.FilmDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.service.MyApi
import com.sixnez.service.getToken
import kotlinx.coroutines.*


class ProfileViewModel(
    application: Application
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _films = MutableLiveData<List<FilmDTO>>()
    val films: LiveData<List<FilmDTO>>
        get() = _films

    private val _film = MutableLiveData<FilmDetailledDTO>()
    val film: LiveData<FilmDetailledDTO>
        get() = _film

    var id: String = ""

    init {
        Log.i("ProfileViewModel", "created")
        getFavs()
        doneFilm()
    }

    private fun getFavs() {
        var getFavs = MyApi.retrofitService.getFavs(
            "Bearer "+ getToken(), 0
        )

        coroutineScope.launch {
            try {
                Log.i("getFavs", "started")
                var result = getFavs.await()

                _films.value = result
                Log.i("getFavs", "Succès : " +result.size +" films favoris récupérés")
            } catch (e: Exception) {
                Log.i("Echec", e.message)
            }
        }
    }

    fun getFilmById(idFilm: String) {
        id = idFilm
        var getFilm = MyApi.retrofitService.getFilm(idFilm, "Bearer "+getToken())

        coroutineScope.launch {
            try {
                Log.i("getFilmById", "started")
                var result = getFilm.await()
                _film.value = result
            } catch (e: Exception) {
                Log.i("Echec", e.message)
            }
        }
    }

    fun doneFilm()  {
        _film.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ProfileViewModel", "destroyed")
        viewModelJob.cancel()
    }
}