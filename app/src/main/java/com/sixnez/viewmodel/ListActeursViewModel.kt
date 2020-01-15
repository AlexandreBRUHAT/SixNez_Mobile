package com.sixnez.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sixnez.model.ActeurDTO
import com.sixnez.model.ActeurRequest
import kotlinx.coroutines.*

class ListActeursViewModel(req: ActeurRequest) : ViewModel() {

    private lateinit var request: ActeurRequest

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _acteurs = MutableLiveData<List<ActeurDTO>>()
    val acteurs: LiveData<List<ActeurDTO>>
        get() = _acteurs

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _nbPages = MutableLiveData<Int>()
    val nbPages: LiveData<Int>
        get() = _nbPages

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        this.request = req
        getActorsList()
    }

    private fun getActorsList() {
        _isLoading.value = true
//        coroutineScope.launch {
//            var getMuseesDeferred = MyApi.retrofitService.getMusees(""+request.page,
//                ""+request.rows,
//                ""+request.sort,
//                ""+request.query?.toLowerCase())
//            try {
//                Log.i("getMusee","started")
//                var result = getMuseesDeferred.await()
//                var listResult = result.records
//                _response.value = "Succès: ${listResult.size} musées récupérés"
//
//                var listMusee : MutableList<Musee> = mutableListOf()
//                var index = 0
//                for (record in listResult) {
//                    listMusee.add(index, recordToMusee(record))
//                    Log.i("Musee","+1")
//                    index++
//                }
//                _musees.value = listMusee
//                _currentPage.value = request.page + 1
//                _nbPages.value = (result.nhits / request.rows) + 1
//            } catch (e: Exception) {
//                _response.value = "Echec: ${e.message}"
//            }
//            _isLoading.value = false
//            Log.i("getMusee","done "+_response.value)
//            Log.i("nb pages", ""+nbPages.value)
//        }
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