@file:Suppress("DEPRECATION")

package com.example.vkm.composeble.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkm.AudioPlayerManager
import com.example.vkm.PlayerEventListener
import com.example.vkm.model.Audio

@Composable
fun PlayerScreen(
    playerManager: AudioPlayerManager
){
    var playButtonState by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var startTrack by remember { mutableStateOf("0:00") }
    var endTrack by remember { mutableStateOf("0:00") }

    val listener = object : PlayerEventListener {
        override fun onPositionChanged(currentPosition: Long, duration: Long) {
            sliderPosition =  currentPosition.toFloat() / duration.toFloat()
            startTrack = playerManager.getFormatTime(currentPosition)
            endTrack = playerManager.getFormatTime(duration-currentPosition)
        }
        override fun onPlayerState(state: Boolean) {
            playButtonState = state
        }
        override fun onPlayingAudio(audio: Audio) {

        }
    }
    playerManager.setPlayerEventListener(listener)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            modifier = Modifier
                .size(300.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ){
            Text(text = "IMAGE")
        }

        Card( // управление аудио
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ){
            Row( //время и скрол
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier.weight(0.1f),
                    contentAlignment = Alignment.Center,
                    content = {Text(text = startTrack, fontSize = 11.sp)}
                )
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        playerManager.seekToFloat(it)
                        },
                    modifier = Modifier.weight(0.8f),
                    colors = SliderDefaults.colors(
                        Color.Red,
                        Color.Gray
                    )
                )
                Box(
                    modifier = Modifier.weight(0.1f),
                    contentAlignment = Alignment.Center,
                    content = { Text(text = "- $endTrack", fontSize = 11.sp) }
                )
            }

            Column( // название и артист
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                val currentAudio by playerManager.currentAudio.observeAsState()
                Text(text = currentAudio?.title ?: " ",
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false
                )
                Text(text = currentAudio?.artist ?: " ",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false
                )
            }

            Row(//кнопки
                modifier = Modifier.padding(5.dp)
            ){
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        modifier = Modifier
                            .size(75.dp),
                        onClick = { playerManager.previousAudio() },
                        content = { Icon(
                            imageVector = Icons.Filled.KeyboardDoubleArrowLeft,
                            contentDescription = "Предыдущий трек",
                            modifier = Modifier.size(50.dp),
                            tint = MaterialTheme.colorScheme.inverseSurface)}
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        modifier = Modifier.size(75.dp),
                        onClick = { playerManager.switchPlaying() },
                        content = {
                            if (playButtonState){
                                Icon(
                                    imageVector = Icons.Filled.Pause,
                                    contentDescription = "Play",
                                    modifier = Modifier.size(50.dp),
                                    tint = MaterialTheme.colorScheme.inverseSurface)
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "Pause",
                                    modifier = Modifier.size(50.dp),
                                    tint = MaterialTheme.colorScheme.inverseSurface)
                            }
                        }
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        modifier = Modifier.size(75.dp),
                        onClick = { playerManager.nextAudio() },
                        content = { Icon(
                            imageVector = Icons.Filled.KeyboardDoubleArrowRight,
                            contentDescription = "Следующий трек",
                            modifier = Modifier.size(50.dp),
                            tint = MaterialTheme.colorScheme.inverseSurface)}
                    )
                }
            }
        }

        Card( // дополнительные функции
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ){
            IconButton(
                modifier = Modifier.size(50.dp),
                onClick = {  },
                content = { Icon(
                    imageVector = Icons.Filled.Equalizer,
                    contentDescription = "Эквалайзер",
                    modifier = Modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.inverseSurface)}
            )
        }
    }
}