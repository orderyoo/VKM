package com.example.vkm.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

data class Audios(
    val response: ResponseAudio
)

data class ResponseAudio(
    val count: Int,
    val items: List<Audio>
)
@Immutable
data class Audio(
    val artist: String,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val title: String,
    val duration: Int,
    @SerializedName("access_key")
    val accessKey: String,
    val ads: Ads,
    @SerializedName("is_explicit")
    val isExplicit: Boolean,
    @SerializedName("is_focus_track")
    val isFocusTrack: Boolean,
    @SerializedName("is_licensed")
    val isLicensed: Boolean,
    @SerializedName("track_code")
    val trackCode: String,
    val url: String,
    val date: Long,
    @SerializedName("main_artists")
    val mainArtists: List<MainArtist>,
    @SerializedName("short_videos_allowed")
    val shortVideosAllowed: Boolean,
    @SerializedName("stories_cover_allowed")
    val storiesAllowed: Boolean,
    @SerializedName("release_audio_id")
    val storiesCoverAllowed: Boolean
)

data class Ads(
    @SerializedName("content_id")
    val contentId: String,
    val duration: String,
    @SerializedName("account_age_type")
    val accountAgeType: String,
    val puid1: String,
    val puid22: String
)

data class MainArtist(
    val name: String,
    val domain: String,
    val id: String,
    @SerializedName("is_followed")
    val isFollowed: Boolean,
    @SerializedName("can_follow")
    val canFollow: Boolean
)






