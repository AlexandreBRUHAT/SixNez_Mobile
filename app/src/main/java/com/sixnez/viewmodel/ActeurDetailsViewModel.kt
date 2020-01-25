package com.sixnez.viewmodel

import com.sixnez.model.ActeurDetailledDTO
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.ActeurDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.service.MyApi
import com.sixnez.service.getToken
import kotlinx.coroutines.*

class ActeurDetailsViewModel(
    application: Application,
    private val monActeur: ActeurDetailledDTO
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _acteur = MutableLiveData<ActeurDetailledDTO>()
    val acteur: LiveData<ActeurDetailledDTO>
        get() = _acteur

    private val _film = MutableLiveData<FilmDetailledDTO>()
    val film: LiveData<FilmDetailledDTO>
        get() = _film

    init {
        Log.i("ActeurDetailsViewModel", "created")
        _acteur.value = monActeur
    }

    fun getFilmById(idFilm: String) {
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

    override fun onCleared() {
        super.onCleared()
        Log.i("ActeurDetailsViewModel", "destroyed")
        viewModelJob.cancel()
    }
}
