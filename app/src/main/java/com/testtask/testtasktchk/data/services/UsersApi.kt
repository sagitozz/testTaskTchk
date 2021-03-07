package com.testtask.testtasktchk.data.services

import com.testtask.testtasktchk.data.entities.UserList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @autor d.snytko
 */
interface UsersApi {

    @GET("/search/users")
    fun getUsersFromQuery(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int = DEFAULT_AMOUNT_USERS
    ): Single<UserList>

    companion object {
        private const val DEFAULT_AMOUNT_USERS = 30
    }
}
