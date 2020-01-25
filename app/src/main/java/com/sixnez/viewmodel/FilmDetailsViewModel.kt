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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val _imgLoaded = MutableLiveData<Boolean>()
    val imgLoaded: LiveData<Boolean>
        get() = _imgLoaded

    var id : FilmIdDTO

    init {
        Log.i("FilmDetailsViewModel", "created")
        _film.value = monFilm
        id = idDTO
        favDone()
        getPicture()
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

    fun getPicture() {
        if (_film.value?.imgURL != null) {
            _imgLoaded.value = true
            return
        }

        var ids = ArrayList<FilmIdDTO>()
        ids.add(id)
        var getPictures = MyApi.retrofitService.getPictures(ids, "Bearer "+getToken())

        coroutineScope.launch {
            try {
                Log.i("getPictures", "started")
                var result = getPictures.await()
                _film.value?.imgURL = result[0].imgURL
                _imgLoaded.value = true
            } catch (e: Exception) {
                Log.i("Echec", e.message+"")
            }
        }
    }

    fun fav() {
        Log.i("Fav", "clicked")
        if (_film.value?.fav == true) {
            _film.value?.fav = false
            deleteFav()
            _favDeleted.value = true
        } else {
            _film.value?.fav = true
            addFav()
            _favAdded.value = true
        }
     }

    private fun deleteFav() {
        MyApi.retrofitService.deleteFavs( "Bearer "+getToken(), idDTO).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    Log.i("onResponse", ""+response.code())

                    if (response.code() != 200) {
                        Log.i("delete fav", "KO")
                    } else {
                        Log.i("delete fav", "OK")
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun addFav() {
        MyApi.retrofitService.setFavs( "Bearer "+getToken(), idDTO).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    Log.i("onResponse", ""+response.code())

                    if (response.code() != 200) {
                        Log.i("add fav", "KO")
                    } else {
                        Log.i("add fav", "OK")
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
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
