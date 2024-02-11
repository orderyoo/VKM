package com.example.vkm.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vkm.ApplicationVKM
import com.example.vkm.ChangeState
import com.example.vkm.SecurityData
import com.example.vkm.composeble.DragHandle
import com.example.vkm.composeble.ModalBottomSheetContent
import com.example.vkm.composeble.SheetDragHandlePlayer
import com.example.vkm.composeble.screens.ArtistScreen
import com.example.vkm.composeble.screens.HomeScreen
import com.example.vkm.composeble.screens.ListPlaylistScreen
import com.example.vkm.composeble.screens.PlayerScreen
import com.example.vkm.composeble.screens.PlaylistScreen
import com.example.vkm.composeble.screens.SearchScreen
import com.example.vkm.composeble.screens.SettingsScreen
import com.example.vkm.model.Audio
import com.example.vkm.model.Playlist
import com.example.vkm.ui.theme.VKMTheme
import com.example.vkm.viewmodels.HomeViewModel
import com.example.vkm.viewmodels.HomeViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val playerManager = (application as ApplicationVKM).playerManager

        setContent {

            val scope = rememberCoroutineScope()
            val scaffoldState = rememberBottomSheetScaffoldState()
            val sheetState = rememberModalBottomSheetState()
            var isVisibleBottomSheet by remember { mutableStateOf(false) }
            var audioForBottomSheet: Audio? = null
            val navController = rememberNavController()

            val listener = object : ChangeState {
                override fun onBottomSheet(audio: Audio) {
                    audioForBottomSheet = audio
                    isVisibleBottomSheet = !isVisibleBottomSheet
                }
                override fun onBottomSheetScaffold() {
                    scope.launch { scaffoldState.bottomSheetState.expand() }
                }
            }

            VKMTheme {

                NavHost(
                    navController = navController,
                    startDestination = "home_screen"
                ){
                    composable("home_screen") {
                        HomeScreen(
                            context = this@HomeActivity,
                            viewModel = viewModel,
                            playerManager = playerManager,
                            navController = navController,
                            listener = listener
                        )
                    }
                    composable("playlist_screen/{playlistId}") {
                            backStackEntry ->
                        PlaylistScreen(
                            context = this@HomeActivity,
                            id = backStackEntry.arguments?.getString("playlistId"),
                            playerManager = playerManager,
                            listener = listener
                        )
                    }
                    composable("search_screen/{searchLine}"){
                            backStackEntry ->
                        SearchScreen(
                            context = this@HomeActivity,
                            searchLine = backStackEntry.arguments?.getString("searchLine"),
                            playerManager = playerManager,
                            navController = navController,
                            listener = listener
                        )
                    }
                    composable("artist_screen/{artistId}"){
                            backStackEntry ->
                        ArtistScreen(
                            context = this@HomeActivity,
                            artistId = backStackEntry.arguments?.getString("artistId"),
                            playerManager = playerManager,
                            navController = navController,
                            listener = listener
                        )

                    }
                    composable("list_playlist_screen/{playlists}"){
                            backStackEntry ->
                        backStackEntry.arguments?.getString("playlists").let {
                            val decodedStr = Uri.decode(it)
                            val listPlaylistType = object : TypeToken<List<Playlist>>() {}.type
                            val listPlaylist = Gson().fromJson<List<Playlist>>(decodedStr, listPlaylistType)

                            ListPlaylistScreen(
                                navController = navController,
                                list = listPlaylist)
                        }
                    }
                    composable("info_screen"){
                        SettingsScreen()
                    }
                }

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = { PlayerScreen(playerManager) },
                    sheetPeekHeight = 50.dp,
                    sheetShape = RoundedCornerShape(0.dp),
                    sheetDragHandle = {
                        SheetDragHandlePlayer(playerManager, listener)
                    }
                ) {}

                if (isVisibleBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            isVisibleBottomSheet = false
                        },
                        modifier = Modifier
                            .padding(5.dp),
                        sheetState = sheetState,
                        shape = RoundedCornerShape(15.dp),
                        dragHandle = { DragHandle(audioForBottomSheet) },
                        content = { ModalBottomSheetContent(audioForBottomSheet, navController, listener) }
                    )
                }

            }
        }
    }

    fun logOutFromAccount(){
        val securityData = SecurityData(this)
        securityData.clearData()
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

}






