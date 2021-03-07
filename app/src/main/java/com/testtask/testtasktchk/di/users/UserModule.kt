package com.testtask.testtasktchk.di.users

import com.testtask.testtasktchk.data.repository.UsersRepository
import com.testtask.testtasktchk.data.repository.UsersRepositoryImpl
import com.testtask.testtasktchk.data.services.UsersApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

/**
 * @autor d.snytko
 */
@Module
interface UserModule {

    @Binds
    @Reusable
    fun bindUserRepository(impl: UsersRepositoryImpl): UsersRepository

    companion object {

        @Provides
        fun provideUsersApi(retrofit: Retrofit) : UsersApi{
            return retrofit.create(UsersApi::class.java)
        }
    }
}
