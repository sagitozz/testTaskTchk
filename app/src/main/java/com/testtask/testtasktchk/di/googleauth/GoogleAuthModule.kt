package com.testtask.testtasktchk.di.googleauth

import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.auth.GoogleAuthProviderImpl
import dagger.Binds
import dagger.Module

/**
 * @autor d.snytko
 */
@Module
interface GoogleAuthModule {

    @Binds
    fun bindsGoogleAuthProvider(impl: GoogleAuthProviderImpl): GoogleAuthProvider
}
