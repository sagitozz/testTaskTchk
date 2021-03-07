package com.testtask.testtasktchk.di.auth

import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.auth.GoogleAuthProviderImpl
import dagger.Binds
import dagger.Module

/**
 * @autor d.snytko
 */
@Module
interface AuthModule {

    @Binds
    fun bindsGoogleAuthProvider(impl: GoogleAuthProviderImpl): GoogleAuthProvider
}
