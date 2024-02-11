package com.example.vkm

import com.example.vkm.model.Audio


interface PlayerEventListener {
    fun onPositionChanged(currentPosition: Long, duration: Long)
    fun onPlayerState(state: Boolean)
    fun onPlayingAudio(audio: Audio)
}

interface ObserverUI{
    fun a(list: Audio, item: Audio)
}

interface ChangeState{
    fun onBottomSheet(audio: Audio)
    fun onBottomSheetScaffold()
}

