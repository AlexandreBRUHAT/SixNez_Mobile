package com.sixnez.viewmodel

import com.sixnez.model.ActeurDetailledDTO
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sixnez.model.ActeurDTO
import kotlinx.coroutines.*

class ActeurDetailsViewModel(
    application: Application,
    private val monActeur: ActeurDTO
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _acteur = MutableLiveData<ActeurDetailledDTO>()
    val acteur: LiveData<ActeurDetailledDTO>
        get() = _acteur

    init {
        Log.i("ActeurDetailsViewModel", "created")
        initializeMusee()
    }

    private fun initializeMusee() {
        uiScope.launch {
            initActeur()
        }
    }

    private fun initActeur() {
        //TODO getById
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
        Log.i("ActeurDetailsViewModel", "destroyed")
        viewModelJob.cancel()
    }
}
