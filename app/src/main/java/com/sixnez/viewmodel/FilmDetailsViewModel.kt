package com.sixnez.viewmodel

import com.sixnez.model.FilmDetailledDTO
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.model.FilmDTO
import com.sixnez.model.FilmIdDTO
import com.sixnez.service.MyApi
import com.sixnez.service.getToken
import kotlinx.coroutines.*

class FilmDetailsViewModel(
    application: Application,
    private val monFilm: FilmDetailledDTO,
    private val idDTO: FilmIdDTO
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _film = MutableLiveData<FilmDetailledDTO>()
    val film: LiveData<FilmDetailledDTO>
        get() = _film

    private val _favAdded = MutableLiveData<Boolean>()
    val favAdded: LiveData<Boolean>
        get() = _favAdded

    private val _favDeleted = MutableLiveData<Boolean>()
    val favDeleted: LiveData<Boolean>
        get() = _favDeleted

    lateinit var id : FilmIdDTO

    init {
        Log.i("FilmDetailsViewModel", "created")
        _film.value = monFilm
        id = idDTO
        favDone()
    }

    private val _acteur = MutableLiveData<ActeurDetailledDTO>()
    val acteur: LiveData<ActeurDetailledDTO>
        get() = _acteur

    fun getActeurById(idActeur: String) {
        var getActeur = MyApi.retrofitService.getActeur(idActeur, "Bearer "+getToken())

        coroutineScope.launch {
            try {
                Log.i("getActeurById", "started")
                var result = getActeur.await()
                _acteur.value = result
            } catch (e: Exception) {
                Log.i("Echec", e.message)
            }
        }
    }

    fun fav() {
        Log.i("Fav", "clicked")
        if (_film.value?.fav == true) {
            _film.value?.fav = false
            MyApi.retrofitService.deleteFavs( "Bearer "+getToken(), idDTO)
            _favDeleted.value = true
        } else {
            _film.value?.fav = true
            MyApi.retrofitService.setFavs( "Bearer "+getToken(), idDTO)
            _favAdded.value = true
        }
     }

    fun favDone() {
        _favAdded.value = null
        _favDeleted.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("FilmDetailsViewModel", "destroyed")
        viewModelJob.cancel()
    }
}
