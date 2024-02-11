package com.example.vkm.model

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(){

    private val vkClient = VkClient()

    fun initVkClient(context: Context){
        vkClient.init(context)
    }

    suspend fun getUser(): Users{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getUser()
        }
    }

    suspend fun getPlaylists(): Playlists{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getPlaylists()
        }
    }
    suspend fun getAudios(): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getAudio()
        }
    }
    suspend fun getPlaylistById(id: Int): Playlists1{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getPlaylistById(id)
        }
    }
    suspend fun getAudiosFromPlaylist(album_id: Int): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getAudioFromPlaylist(album_id)
        }
    }
    suspend fun getPlaylistByArtist(artist_id: String): Playlists{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getPlaylistsByArtist(artist_id)
        }
    }
    suspend fun getAudiosByArtist(artist_id: String): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getAudiosByArtist(artist_id)
        }
    }
    suspend fun searchAudio(string: String): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.searchAudio(string)
        }
    }
}

fun toEncodeString(it: Any?): String {
    return Uri.encode(Gson().toJson(it))
}