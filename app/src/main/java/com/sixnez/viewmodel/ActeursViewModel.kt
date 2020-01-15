package com.sixnez.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.ActeurRequest
import kotlinx.coroutines.*


class ActeursViewModel(
    application: Application
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.i("ActeursViewModel", "created")
    }

    private val _request = MutableLiveData<ActeurRequest>()
    val request: LiveData<ActeurRequest>
        get() = _request

    //launch request
    private val _navigateToListActorsFragment = MutableLiveData<ActeurRequest>()
    val navigateToListActorsFragment: LiveData<ActeurRequest>
        get() = _navigateToListActorsFragment

    fun onValidateSearch() {
        _navigateToListActorsFragment.value = request.value
    }

    fun doneNavigating() {
        _navigateToListActorsFragment.value = null
    }

    //alert
    private val _alert = MutableLiveData<String>()

    val alert: LiveData<String>
        get() = _alert

    fun doneAlerting() {
        _alert.value = null
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("ActeursViewModel", "destroyed")
        viewModelJob.cancel()
    }
}