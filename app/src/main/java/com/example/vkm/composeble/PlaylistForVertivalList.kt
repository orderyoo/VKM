package com.example.vkm.composeble

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vkm.model.Playlist
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PlaylistForVerticalList(
    navController: NavController,
    playlist: Playlist
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 5.dp)
            .clickable { navController.navigate("playlist_screen/${playlist.id}") },
        verticalAlignment = Alignment.CenterVertically
    ){
        Card(
            modifier = Modifier
                .size(100.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ){
            @Suppress("DEPRECATION")
            Image(painter = rememberGlidePainter(request = playlist.photo.photo300), contentDescription = null)
        }

        Column(modifier = Modifier.weight(1f)){
            Text(text = playlist.title,
                modifier = Modifier.padding(5.dp),
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                softWrap = false)
            Text(text = playlist.mainArtist?.first()?.name ?: "тут будет группа или пользователь",
                modifier = Modifier.padding(5.dp),
                color = Color.Gray,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                softWrap = false)
        }
    }
}