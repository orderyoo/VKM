@file:Suppress("DEPRECATION")

package com.example.vkm.composeble.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vkm.AudioPlayerManager
import com.example.vkm.ChangeState
import com.example.vkm.activitys.HomeActivity
import com.example.vkm.composeble.AudioForVerticalList
import com.example.vkm.composeble.ConfigAudioList
import com.example.vkm.composeble.LoadingIndicator
import com.example.vkm.model.Audio
import com.example.vkm.model.Playlist
import com.example.vkm.model.User
import com.example.vkm.model.toEncodeString
import com.example.vkm.viewmodels.HomeViewModel
import com.google.accompanist.glide.rememberGlidePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: HomeActivity,
    viewModel: HomeViewModel,
    playerManager: AudioPlayerManager,
    navController: NavController,
    listener: ChangeState
){
    var listPlaylist by remember { mutableStateOf(listOf<Playlist>()) }
    var listAudioForHome by remember { mutableStateOf(listOf<Audio>()) }
    var user by remember { mutableStateOf(listOf<User>()) }

    val configAudioList = ConfigAudioList(
        inverseOnSurface = MaterialTheme.colorScheme.inverseOnSurface,
        outline = MaterialTheme.colorScheme.outline)

    LaunchedEffect(Unit) {
        viewModel.getPlaylists()
        viewModel.userPlaylistList.observe(context) {
            listPlaylist = it.response.items
        }
        viewModel.getAudios()
        viewModel.userAudioList.observe(context) {
            listAudioForHome = it.response.items
        }
        viewModel.getUser()
        viewModel.currentUser.observe(context){
            println(it)
            user = it.response
            println(user)
        }
    }

    if(listPlaylist.isNotEmpty() && listAudioForHome.isNotEmpty() && user.isNotEmpty()) {

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
            rememberTopAppBarState()
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                var text by remember { mutableStateOf("") }
                TopAppBar(
                    title = {
                        OutlinedTextField(value = text,
                            onValueChange = { text = it },
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                            keyboardActions = KeyboardActions(
                                onDone = { navController.navigate("search_screen/${text}") }
                            ),
                            placeholder = { Text(text = "Поиск") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigate("search_screen/${text}") }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "search",
                                tint = MaterialTheme.colorScheme.inverseSurface
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { navController.navigate("info_screen") }
                        ) {
                            Image(
                                painter = rememberGlidePainter(
                                    request = user[0].photo200
                                ),
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)){

                item { // список плейлистов
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                    ) {
                        Row ( verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = "Плейлисты",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Показать все",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        navController.navigate(
                                            "list_playlist_screen/${
                                                toEncodeString(
                                                    listPlaylist
                                                )
                                            }"
                                        )
                                    },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(listPlaylist) { item ->
                                Box(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .width(128.dp)
                                        .clickable {
                                            navController.navigate("playlist_screen/${item.id}")
                                        }
                                ) {
                                    Column {
                                        Card(
                                            modifier = Modifier
                                                .size(125.dp),
                                            shape = RoundedCornerShape(15.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                                        ) {
                                            @Suppress("DEPRECATION")
                                            Image(
                                                painter = rememberGlidePainter(request = item.photo.photo300),
                                                contentDescription = null
                                            )
                                        }
                                        Column(Modifier.padding(5.dp)) {
                                            Text(
                                                text = item.title,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.primary,
                                                overflow = TextOverflow.Ellipsis,
                                                softWrap = false
                                            )
                                            Text(
                                                text = item.mainArtist?.first()?.name ?: "плейлист",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                overflow = TextOverflow.Ellipsis,
                                                softWrap = false
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                items( //cписок моих аудио
                    items = listAudioForHome,
                    key = { audio -> audio.id }
                ) { item ->
                    AudioForVerticalList(
                        playerManager = playerManager,
                        item = item,
                        list = listAudioForHome,
                        listener = listener,
                        configAudioList
                    )
                }

                item {
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