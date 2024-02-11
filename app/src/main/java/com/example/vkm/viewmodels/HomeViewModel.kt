package com.example.vkm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkm.model.Audios
import com.example.vkm.model.Playlists
import com.example.vkm.model.Repository
import com.example.vkm.model.Users
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel(){

    val currentUser = MutableLiveData<Users>()
    val userPlaylistList = MutableLiveData<Playlists>()
    val userAudioList = MutableLiveData<Audios>()

    fun getUser(){
        viewModelScope.launch {
            currentUser.value = repository.getUser()
        }
    }
    fun getPlaylists(){
        viewModelScope.launch {
            userPlaylistList.value = repository.getPlaylists()
        }
    }
    fun getAudios(){
        viewModelScope.launch {
            userAudioList.value = repository.getAudios()
        }
    }

}