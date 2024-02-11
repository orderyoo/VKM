package com.example.vkm.composeble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkm.AudioPlayerManager
import com.example.vkm.ChangeState
import com.example.vkm.model.Audio

@Composable
fun AudioForVerticalList(
    playerManager: AudioPlayerManager,
    item: Audio,
    list: List<Audio>,
    listener: ChangeState,
    configAudioList: ConfigAudioList
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { playerManager.controllerClickOnAudio(list, item) },
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = configAudioList.modifierBox,
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = configAudioList.modifierBoxIcon,
                contentAlignment = Alignment.Center,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Audiotrack,
                        contentDescription = "аудио")
                    //Image(painter = rememberGlidePainter( request = viewModel.currentPlaylist.value!!.photo.photo300), contentDescription = null)}
                }
            )
        }
        Column(modifier = Modifier.weight(1f)){
            Text(text = item.title,
                modifier = configAudioList.modifierText,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                softWrap = false)
            Text(text = item.artist,
                modifier = configAudioList.modifierText,
                color = Color.Gray,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                softWrap = false)
        }

        Text(text = toDateFormatMMSS(item.duration.toLong()),
                color = Color.Gray,
                fontSize = 12.sp)

        IconButton(
            onClick = { listener.onBottomSheet(item) },
            content = {
                Icon(
                    imageVector = Icons.Filled.ArrowForwardIos,
                    contentDescription = "Меню редактирования",
                    modifier = configAudioList.modifierIcon,
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            }
        )
    }
}

class ConfigAudioList(
    inverseOnSurface: Color,
    outline: Color
){
    val modifierRow: Modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .background(
            color = inverseOnSurface,
            shape = RoundedCornerShape(10.dp))
    val modifierBox: Modifier = Modifier.padding(5.dp)
    val modifierBoxIcon: Modifier = Modifier
        .size(50.dp)
        .background(
            color = outline,
            shape = RoundedCornerShape(5.dp)
        )
    val modifierText: Modifier = Modifier.padding(3.dp)
    val modifierIcon: Modifier = Modifier.size(20.dp)
}