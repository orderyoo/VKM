package com.example.vkm.composeble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkm.AudioPlayerManager
import com.example.vkm.ChangeState

@Composable
fun SheetDragHandlePlayer(
    playerManager: AudioPlayerManager,
    listener: ChangeState
){
    val currentAudio by playerManager.currentAudio.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                if(playerManager.currentAudio.value != null )listener.onBottomSheetScaffold() }
            .background(MaterialTheme.colorScheme.onSurface),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
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
    }
}
