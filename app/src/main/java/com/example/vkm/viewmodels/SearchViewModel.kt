package com.example.vkm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkm.model.Audios
import com.example.vkm.model.Repository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    val listAudio = MutableLiveData<Audios>()

    fun searchAudios(string: String){
        viewModelScope.launch {
            listAudio.value = repository.searchAudio(string)
        }
    }
}