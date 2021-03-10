package com.testtask.testtasktchk.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @autor d.snytko
 */
class UserList(
    @SerializedName("items")
    val users: List<User>
)
