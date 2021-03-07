package com.testtask.testtasktchk.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @autor d.snytko
 */
class User(
    @SerializedName("id")
    val id: Long,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
