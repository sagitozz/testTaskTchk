package com.testtask.testtasktchk.di.googleauth

import com.testtask.testtasktchk.auth.GoogleAuthProvider
import com.testtask.testtasktchk.di.app.ApplicationComponent
import dagger.Component

/**
 * @autor d.snytko
 */
interface GoogleAuthComponent {

    val googleAuthProvider: GoogleAuthProvider
}

@Component(
    dependencies = [ApplicationComponent::class],
    modules = [GoogleAuthModule::class]
)
interface GoogleAuthComponentImpl : GoogleAuthComponent {

    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): GoogleAuthComponent
    }
}
