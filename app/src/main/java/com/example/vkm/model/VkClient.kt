package com.example.vkm.model

import android.content.Context
import com.example.vkm.SecurityData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class VkClient {

    private lateinit var securityData: SecurityData

    private val userAgentValue = "KateMobileAndroid/92.2 v1-524 (Android 11; SDK 30; x86; Pixel sdk_gphone_x86_arm; ru)"
    private val client = OkHttpClient()
    private val gson = Gson()

    fun init(context: Context){
        securityData = SecurityData(context)
    }

    suspend fun gets(): Any = withContext(Dispatchers.IO) {
        
    }
    fun getUser(): Users {
        val url = "https://api.vk.com/method/users.get?" +
                "user_id=${securityData.getData("user_id", "0")}&" +
                "fields=photo_200&name_case=nom&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Users::class.java)
        }
    }

    fun getPlaylists(): Playlists{
        val url = "https://api.vk.com/method/audio.getPlaylists?" +
                "owner_id=${securityData.getData("user_id", "0")}&count=100&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Playlists::class.java)
        }
    }

    fun getAudio(): Audios  {
        val url = "https://api.vk.com/method/audio.get?" +
                "owner_id=${securityData.getData("user_id", "0")}&" +
                "count=1000&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Audios::class.java)
        }
    }

    fun getAudioFromPlaylist(album_id: Int): Audios{
        val url = "https://api.vk.com/method/audio.get?" +
                "owner_id=${securityData.getData("user_id", "0")}&" +
                "album_id=$album_id&" +
                "count=1000&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent",userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Audios::class.java)
        }
    }
    fun getPlaylistById(id: Int): Playlists1{
        val url = "https://api.vk.com/method/audio.getPlaylistById?" +
                "owner_id=${securityData.getData("user_id", "0")}&" +
                "playlist_id=$id&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent",userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Playlists1::class.java)
        }
    }
    fun getPlaylistsByArtist(artist_id: String): Playlists{

        val url = "https://api.vk.com/method/audio.getAlbumsByArtist?" +
                "artist_id=$artist_id&" +
                "count=100&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent",userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Playlists::class.java)
        }
    }
    fun getAudiosByArtist(artist_id: String): Audios{

        val url = "https://api.vk.com/method/audio.getAudiosByArtist?" +
                "artist_id=$artist_id&" +
                "count=1000&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent",userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Audios::class.java)
        }
    }
    fun searchAudio(search: String): Audios{
        val url = "https://api.vk.com/method/audio.search?" +
                "q=$search&" +
                "count=100&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        response.use {
            val responseData = it.body?.string()
            return gson.fromJson(responseData, Audios::class.java)
        }
    }

    fun addAudioToUser(audio_id: Int){
        val url = "https://api.vk.com/method/audio.add?" +
                "owner_id=${securityData.getData("user_id", "0")}&" +
                "audio_id=$audio_id&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"
        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        val responseData = response.body?.string()
        println(responseData)
    }

    fun deleteAudioToUser(audio_id: Int){
        val url = "https://api.vk.com/method/audio.delete?" +
                "owner_id=${securityData.getData("user_id", "0")}&" +
                "audio_id=$audio_id&" +
                "access_token=${securityData.getData("token", "0")}&v=5.131"
        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", userAgentValue)
            .build()

        val response = client.newCall(request).execute()
        val responseData = response.body?.string()
        println(responseData)
    }
}