package com.example.vkm

import android.app.Application
import com.example.vkm.model.Dependencies

class ApplicationVKM: Application() {

    lateinit var playerManager: AudioPlayerManager

    override fun onCreate() {
        super.onCreate()
        Dependencies.repository.initVkClient(this)
        playerManager = AudioPlayerManager(this)
    }
}