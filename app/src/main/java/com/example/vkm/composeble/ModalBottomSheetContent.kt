package com.example.vkm.composeble

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MicExternalOn
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vkm.ChangeState
import com.example.vkm.model.Audio

@Composable
fun ModalBottomSheetContent(
    audio: Audio?,
    navController: NavController,
    listener: ChangeState
){
    val listButtons = listOf(
        IconTextContainer("Добавить в плейлист", Icons.Filled.PlaylistAdd),
        IconTextContainer("Перейти к исполнителю", Icons.Filled.MicExternalOn),
        IconTextContainer("Перейти к альбому", Icons.Filled.Album)
    )

    Column {
        listButtons.forEachIndexed { index, it ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when(index){
                                0 -> println("add playlist")
                                1 -> {
                                    listener.onBottomSheet(audio!!)
                                    navController.navigate("artist_screen/${audio.mainArtists[0].id}")
                                }
                                2 -> {
                                    listener.onBottomSheet(audio!!)
                                    //navController.navigate("playlist_screen/${audio!!.}")
                                }
                            }
                        }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Box(
                        modifier = Modifier.weight(0.15f),
                        contentAlignment = Alignment.Center,
                        content = { Icon(it.icon, null) }
                    )
                    Box(
                        modifier = Modifier.weight(0.85f),
                        contentAlignment = Alignment.Center,
                        content = {
                            Text(text = it.title,
                                fontSize = 18.sp)
                        }
                    )
                }
            }
        }
    }
}
data class IconTextContainer (
    val title: String,
    val icon: ImageVector
)