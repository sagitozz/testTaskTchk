package com.testtask.testtasktchk.di.auth

import android.content.Context
import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.di.app.ApplicationComponent
import dagger.BindsInstance
import dagger.Component

/**
 * @autor d.snytko
 */
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [AuthModule::class]
)
interface AuthComponent {

    val googleAuthProvider: GoogleAuthProvider

    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): AuthComponent
    }
}
