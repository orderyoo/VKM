package com.example.vkm.composeble

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkm.model.Audio

@Composable
fun DragHandle(
    audio: Audio?
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier.weight(0.20f),
            contentAlignment = Alignment.Center
        ){
            Card(
                modifier = Modifier
                    .size(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outline),
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon( imageVector = Icons.Filled.Audiotrack,
                                contentDescription = "адио")
                        }
                    )
                    //Image(painter = rememberGlidePainter( request = viewModel.currentPlaylist.value!!.photo.photo300), contentDescription = null)}
                }
            )
        }
        Column(modifier = Modifier.weight(0.80f)) {
            Text(text = audio!!.title,
                modifier = Modifier
                    .padding(5.dp),
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                softWrap = false)
            Text(text = audio.artist,
                modifier = Modifier
                    .padding(5.dp),
                color = Color.Gray,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                softWrap = false)
        }
    }
}