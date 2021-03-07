package com.testtask.testtasktchk.data.repository

import com.testtask.testtasktchk.data.entities.UserList
import com.testtask.testtasktchk.data.services.UsersApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * @autor d.snytko
 */
interface UsersRepository {

    fun getUsers(query: String, page: Int) : Single<UserList>
}

class UsersRepositoryImpl @Inject constructor(private val api: UsersApi) : UsersRepository {

    override fun getUsers(query: String, page: Int): Single<UserList> {
       return api.getUsersFromQuery(query, page)
    }
}
