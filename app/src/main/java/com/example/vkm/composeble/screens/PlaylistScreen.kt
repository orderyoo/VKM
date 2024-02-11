@file:Suppress("DEPRECATION", "IMPLICIT_CAST_TO_ANY", "NAME_SHADOWING")

package com.example.vkm.composeble.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkm.AudioPlayerManager
import com.example.vkm.ChangeState
import com.example.vkm.activitys.HomeActivity
import com.example.vkm.composeble.AudioForVerticalList
import com.example.vkm.composeble.ConfigAudioList
import com.example.vkm.composeble.IconTextContainer
import com.example.vkm.composeble.LoadingIndicator
import com.example.vkm.composeble.toDateFormatDDMMYY
import com.example.vkm.model.Audio
import com.example.vkm.model.Dependencies
import com.example.vkm.model.Playlist
import com.example.vkm.viewmodels.PlaylistScreenViewModel
import com.google.accompanist.glide.rememberGlidePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    context: HomeActivity,
    id: String?,
    viewModel: PlaylistScreenViewModel = PlaylistScreenViewModel(Dependencies.repository),
    playerManager: AudioPlayerManager,
    listener: ChangeState
){

    var playlist by remember { mutableStateOf(mutableListOf<Playlist>())}
    var listAudio by remember { mutableStateOf(listOf<Audio>()) }

    val configAudioList = ConfigAudioList(
        inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface,
        outline = MaterialTheme.colorScheme.outline)

    LaunchedEffect(Unit) {
        val id = id?.toInt()

        viewModel.getPlaylistById(id?: 1)
        viewModel.playlistInfo.observe(context){
            playlist.add(it.response)
        }
        viewModel.getAudiosFromPlaylist(id?: 1)
        viewModel.listAudio.observe(context) {
            listAudio = it.response.items
        }
    }

    if(playlist.isNotEmpty() && listAudio.isNotEmpty()){

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = "Плейлист")
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },

                    scrollBehavior = scrollBehavior
                )
            },
        ){ innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Card(
                                modifier = Modifier
                                    .size(175.dp)
                                    .padding(5.dp),
                                shape = RoundedCornerShape(15.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                                content = {
                                    Image(
                                        painter = rememberGlidePainter(
                                            request = playlist[0].photo.photo300),
                                        contentDescription = null
                                    )
                                }
                            )
                            Text(
                                text = playlist[0].title,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text =playlist[0].mainArtist?.first()?.name
                                    ?: "Автор",
                                color = Color.Gray
                            )
                        }

                        val listCard = listOf(
                            IconTextContainer("Прослушиваний", Icons.Filled.Headset),
                            IconTextContainer("Жанр", Icons.Filled.Style),
                            IconTextContainer("Год выхода", Icons.Filled.DateRange)
                        )
                        Row { // карты прослушивания и даты
                            listCard.forEachIndexed { index, it ->
                                Card(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error)
                                ){
                                    Icon(
                                        imageVector = it.icon,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(start = 3.dp, top = 3.dp)
                                            .size(20.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(3.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = when(index){
                                                0 -> formatNumber(playlist[0].plays)
                                                1 -> playlist[0].genres?.firstOrNull()?.name ?: "Неизвестен"
                                                2 -> playlist[0].year
                                                    ?: toDateFormatDDMMYY(playlist[0].updateTime)
                                                else -> {""}
                                            },
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                if(playlist[0].description != ""){
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                            content = {
                                Text(text = playlist[0].description,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 14.sp)
                            }
                        )
                    }
                }

                items(
                    items = listAudio,
                    key = {audio -> audio.id}
                ){ item ->
                    AudioForVerticalList(
                        playerManager = playerManager,
                        item = item,
                        list = listAudio,
                        listener = listener,
                        configAudioList,
                    )
                }
                item{
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ){ LoadingIndicator() }
    }
}

fun formatNumber(number: Int): String {
    return when {
        number < 10000 -> number.toString()
        number < 1_000_000 -> String.format("%.1f K", number / 1000.0)
        else -> String.format("%.1f M", number / 1_000_000.0)
    }
}


