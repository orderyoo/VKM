package com.example.vkm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkm.model.Audios
import com.example.vkm.model.Playlists1
import com.example.vkm.model.Repository
import kotlinx.coroutines.launch

class PlaylistScreenViewModel(private val repository: Repository) : ViewModel() {

    val listAudio = MutableLiveData<Audios>()
    val playlistInfo = MutableLiveData<Playlists1>()

    fun getAudiosFromPlaylist(id: Int){
        viewModelScope.launch {
            listAudio.value = repository.getAudiosFromPlaylist(id)
        }
    }
    fun getPlaylistById(id: Int){
        viewModelScope.launch {
            playlistInfo.value = repository.getPlaylistById(id)
        }
    }
}