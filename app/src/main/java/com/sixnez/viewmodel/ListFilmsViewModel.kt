package com.sixnez.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sixnez.model.FilmDTO
import com.sixnez.model.FilmDetailledDTO
import com.sixnez.model.FilmRequest
import com.sixnez.service.MyApi
import com.sixnez.service.MyApiService
import com.sixnez.service.getToken
import kotlinx.coroutines.*

class ListFilmsViewModel(req: FilmRequest) : ViewModel() {

    private lateinit var request: FilmRequest

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _films = MutableLiveData<List<FilmDTO>>()
    val films: LiveData<List<FilmDTO>>
        get() = _films

    private val _film = MutableLiveData<FilmDetailledDTO>()
    val film: LiveData<FilmDetailledDTO>
        get() = _film

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _lastPage = MutableLiveData<Boolean>()
    val lastPage: LiveData<Boolean>
        get() = _lastPage

    var id: String = ""

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        this.request = req
        getFilmsList()
        _currentPage.value = 0
        doneFilm()
        _lastPage.value = false
    }

    private fun getFilmsList() {
        _isLoading.value = true
        var getFilms = MyApi.retrofitService.getFilms(
            request.page,
            request.genre,
            "" + request.query,
            request.annee,
            "Bearer "+ getToken()
        )

        coroutineScope.launch {
            try {
                Log.i("getFilms", "started")
                var result = getFilms.await()

                _films.value = result
                _lastPage.value = (result.size < request.rows)
                _currentPage.value = request.page + 1
                _isLoading.value = false
                Log.i("getFilms", "Succès : " +result.size +" films récupérés")
            } catch (e: Exception) {
                Log.i("Echec", e.message)
            }
        }
    }

    fun loading(b: Boolean) {
        _isLoading.value = b
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

    fun goToPrecedentPage() {
        this.request.page = this.request.page - request.rows
        getFilmsList()
    }

    fun goToNextPage() {
        this.request.page = this.request.page + request.rows
        getFilmsList()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}