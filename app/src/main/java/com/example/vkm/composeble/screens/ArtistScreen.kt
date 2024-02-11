package com.example.vkm.composeble.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.vkm.AudioPlayerManager
import com.example.vkm.ChangeState
import com.example.vkm.activitys.HomeActivity
import com.example.vkm.composeble.AudioForVerticalList
import com.example.vkm.composeble.ConfigAudioList
import com.example.vkm.composeble.LoadingIndicator
import com.example.vkm.model.Audio
import com.example.vkm.model.Dependencies
import com.example.vkm.model.Playlist
import com.example.vkm.viewmodels.ArtistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    context: HomeActivity,
    viewModel: ArtistViewModel = ArtistViewModel(Dependencies.repository),
    artistId: String?,
    playerManager: AudioPlayerManager,
    navController: NavController,
    listener: ChangeState
){
    var playlists by remember { mutableStateOf(listOf<Playlist>()) }
    var listAudio by remember { mutableStateOf(listOf<Audio>()) }

    val configAudioList = ConfigAudioList(
        inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface,
        outline = MaterialTheme.colorScheme.outline)

    LaunchedEffect(Unit) {

        viewModel.getPlaylistsArtist(artistId ?: "")
        viewModel.playlists.observe(context){
            playlists = it.response.items
        }
        viewModel.getAudiosArtist(artistId ?: "")
        viewModel.listAudio.observe(context) {
            listAudio = it.response.items
        }
    }

    if(playlists.isNotEmpty() && listAudio.isNotEmpty()){

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = listAudio[0].artist)
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
        ) { innerPadding ->
            LazyColumn(Modifier.padding(innerPadding)){
                items(listAudio){ item ->
                    AudioForVerticalList(
                        playerManager = playerManager,
                        item = item,
                        list = listAudio,
                        listener = listener,
                        configAudioList
                    )
                }
            }
        }
    } else {
        LoadingIndicator()
    }
}