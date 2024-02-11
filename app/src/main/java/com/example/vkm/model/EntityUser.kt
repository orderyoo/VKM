package com.example.vkm.model

import com.google.gson.annotations.SerializedName

data class Users(
    val response: List<User>
)

data class User(
    val id: Int,
    @SerializedName("photo_200")
    val photo200: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val can_access_closed: Boolean,
    val is_closed: Boolean
)