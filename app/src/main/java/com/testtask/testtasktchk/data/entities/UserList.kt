package com.testtask.testtasktchk.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @autor d.snytko
 */
class UserList(
    @SerializedName("items")
    @Expose
    val users: List<User>
)
