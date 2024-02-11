@file:Suppress("DEPRECATION")

package com.example.vkm

import android.content.Context
import android.net.Uri
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.vkm.model.Audio
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.text.SimpleDateFormat
import java.util.Date

class AudioPlayerManager(private val context: Context) {

    private val player = SimpleExoPlayer.Builder(context).build()
    private var playerEventListener: PlayerEventListener? = null

    val currentAudio =  MutableLiveData<Audio>()
    var playingPlaylist = listOf<Audio>()

    private val handler = Handler()
    private val updateIntervalMs: Long = 1000
    private val dateFormat = SimpleDateFormat("mm:ss")
    init{
        player.addListener(object : Player.Listener{
            @Deprecated("Deprecated in Java")
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                playerEventListener?.onPlayerState(playWhenReady)
                if (playWhenReady)
                    startSeekBarUpdate()
                else
                    handler.removeCallbacksAndMessages(null)
            }
            @Deprecated("Deprecated in Java")
            override fun onPositionDiscontinuity(reason: Int) {
                super.onPositionDiscontinuity(reason)
                currentAudio.value = playingPlaylist[player.currentMediaItemIndex]
            }
        })
    }

    private fun startSeekBarUpdate() {
        handler.post(object : Runnable {
            override fun run() {
                playerEventListener?.onPositionChanged(player.currentPosition, player.duration )
                handler.postDelayed(this, updateIntervalMs)
            }
        })
    }
    fun controllerClickOnAudio(list: List<Audio>, item: Audio){
        if (playingPlaylist.isEmpty() || playingPlaylist != list) {
            if (playingPlaylist.isNotEmpty())
                clearMediaItems()
            setPlaylist(list)
            playingPlaylist = list
        }
        if (currentAudio.value == null || currentAudio.value!!.id != item.id){
            player.seekTo(list.indexOf(item), 0)
            player.playWhenReady = true
            currentAudio.value = item
        } else {
            switchPlaying()
        }
    }
    private fun setPlaylist(list: List<Audio>){
        val userAgent = Util.getUserAgent(
            context,
            "KateMobileAndroid/92.2 v1-524 (Android 11; SDK 30; x86; Pixel sdk_gphone_x86_arm; ru)")
        val mediaItems = list.map { MediaItem.fromUri(Uri.parse(it.url)) }

        val mediaSource = ConcatenatingMediaSource()

        for (mediaItem in mediaItems) {
            mediaSource.addMediaSource(
                ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                    .createMediaSource(mediaItem)
            )
        }
        player.prepare(mediaSource)
    }
    fun nextAudio(){
        player.next()
        player.playWhenReady = true
    }

    fun previousAudio(){
        player.previous()
        player.playWhenReady = true
    }

    fun switchPlaying(){
        player.playWhenReady = !player.isPlaying
    }

    fun seekToFloat(positionSlider: Float) {
        player.seekTo((positionSlider * player.duration).toLong())
    }

    fun getFormatTime(time: Long): String{
        val date = Date(time)
        return dateFormat.format(date)
    }

    fun setPlayerEventListener(listener: PlayerEventListener) {
        playerEventListener = listener
    }

    private fun clearMediaItems() {
        player.clearMediaItems()
    }
}