package com.testtask.testtasktchk.di

import com.testtask.testtasktchk.app.App
import com.testtask.testtasktchk.di.app.ApplicationComponent
import com.testtask.testtasktchk.di.googleauth.GoogleAuthComponent
import com.testtask.testtasktchk.di.users.UserComponent
import dagger.Component
import javax.inject.Singleton

/**
 * @autor d.snytko
 */
@Component(
    dependencies = [
        ApplicationComponent::class,
        GoogleAuthComponent::class,
        UserComponent::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)

    @Component.Factory
    interface Factory {
        fun create(
            applicationComponent: ApplicationComponent,
            googleAuthComponent: GoogleAuthComponent,
            userComponent: UserComponent
        ): AppComponent
    }

    fun activityComponent(): ActivityComponent
}
