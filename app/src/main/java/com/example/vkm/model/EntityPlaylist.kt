package com.example.vkm.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
data class Playlists(
    val response: Response
)
data class Playlists1(
    val response: Playlist
)

//Контейнер с плейлистами
data class Response(
    val count: Int,
    val items: List<Playlist>,
    @SerializedName("next_from")
    val nextFrom: String,
    val profiles: List<Any>,
    val groups: List<Any>
)

//Плейлисты
@Immutable
data class Playlist(
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Long,
    val type: Int,
    val title: String,
    val description: String,
    val count: Int,
    val followers: Int,
    val plays: Int,
    @SerializedName("create_time")
    val createTime: Long,
    @SerializedName("update_time")
    val updateTime: Long,
    val genres: List<Genre>?,
    @SerializedName("is_following")
    val isFollowing: Boolean,
    val year: String?,
    val original: Original?,
    val followed: Followed?,
    val photo: Photo,
    val permissions: Permissions,
    @SerializedName("subtitle_badge")
    val subtitleBadge: Boolean,
    @SerializedName("play_button")
    val playButton: Boolean,
    @SerializedName("access_key")
    val accessKey: String,
    @SerializedName("is_explicit")
    val isExplicit: Boolean?,
    @SerializedName("main_artists")
    val mainArtist: List<MainArtist>?,
    @SerializedName("album_type")
    val albumType: String,
    val exclusive: Boolean
    )

//Фото
data class Photo(
    val width: Int,
    val height: Int,
    @SerializedName("photo_34")
    val photo34: String,
    @SerializedName("photo_68")
    val photo68: String,
    @SerializedName("photo_135")
    val photo135: String,
    @SerializedName("photo_270")
    val photo270: String,
    @SerializedName("photo_300")
    val photo300: String,
    @SerializedName("photo_600")
    val photo600: String,
    @SerializedName("photo_1200")
    val photo1200: String
)

//Разрешения плейлиста
data class Permissions(
    val play: Boolean,
    val share: Boolean,
    val edit: Boolean,
    val follow: Boolean,
    val delete: Boolean,
    @SerializedName("boom_download")
    val boomDownload: Boolean
)

data class Genre(
    val id: Int,
    val name: String
)

data class Original(
    @SerializedName("playlist_id")
    val playlistId: Long,
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("access_key")
    val accessKey: String
)

data class Followed(
    @SerializedName("playlist_id")
    val playlistId: Int,
    @SerializedName("owner_id")
    val ownerId: Long
)

