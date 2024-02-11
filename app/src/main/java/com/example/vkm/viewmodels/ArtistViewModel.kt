package com.example.vkm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkm.model.Audios
import com.example.vkm.model.Playlists
import com.example.vkm.model.Repository
import kotlinx.coroutines.launch

class ArtistViewModel(private val repository: Repository): ViewModel() {

    val listAudio = MutableLiveData<Audios>()
    val playlists = MutableLiveData<Playlists>()

    fun getPlaylistsArtist(artistId: String){
        viewModelScope.launch {
            playlists.value = repository.getPlaylistByArtist(artistId)
        }
    }
    fun getAudiosArtist(artistId: String){
        viewModelScope.launch {
           listAudio.value = repository.getAudiosByArtist(artistId)
        }
    }
}