package com.sixnez.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sixnez.model.ActeurDTO
import com.sixnez.model.ActeurDetailledDTO
import com.sixnez.model.ActeurRequest
import com.sixnez.service.MyApi
import com.sixnez.service.getToken
import kotlinx.coroutines.*

class ListActeursViewModel(req: ActeurRequest) : ViewModel() {

    private var request: ActeurRequest

    private val _acteurs = MutableLiveData<List<ActeurDTO>>()
    val acteurs: LiveData<List<ActeurDTO>>
        get() = _acteurs

    private val _acteur = MutableLiveData<ActeurDetailledDTO>()
    val acteur: LiveData<ActeurDetailledDTO>
        get() = _acteur

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _lastPage = MutableLiveData<Boolean>()
    val lastPage: LiveData<Boolean>
        get() = _lastPage

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        this.request = req
        getActorsList()
        _currentPage.value = 0
        doneActeur()
        _lastPage.value = false
    }

    private fun getActorsList() {
        _isLoading.value = true
        var getActeurs = MyApi.retrofitService.getActeurs(
            request.page,
            "" + request.query,
            request.metier,
            "Bearer "+ getToken()
        )

        coroutineScope.launch {
            try {
                Log.i("getActeurs", "started")
                var result = getActeurs.await()

                _acteurs.value = result
                _lastPage.value = (result.size < request.rows)
                _currentPage.value = request.page + 1
                _isLoading.value = false
                Log.i("getActeurs", "Succès : " +result.size +" acteurs récupérés")
            } catch (e: Exception) {
                Log.i("Echec", e.message)
            }
        }
    }

    fun loading(b: Boolean) {
        _isLoading.value = b
    }

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

    fun doneActeur()  {
        _acteur.value = null
    }

    fun goToPrecedentPage() {
        this.request.page = this.request.page - request.rows
        getActorsList()
    }

    fun goToNextPage() {
        this.request.page = this.request.page + request.rows
        getActorsList()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}