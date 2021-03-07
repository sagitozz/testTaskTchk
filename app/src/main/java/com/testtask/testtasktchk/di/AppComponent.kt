package com.testtask.testtasktchk.di

import com.testtask.testtasktchk.app.App
import com.testtask.testtasktchk.di.app.ApplicationComponent
import com.testtask.testtasktchk.di.auth.AuthComponent
import dagger.Component
import javax.inject.Singleton

/**
 * @autor d.snytko
 */
@Component(
    dependencies = [
        ApplicationComponent::class,
        AuthComponent::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)

    @Component.Factory
    interface Factory {
        fun create(
            applicationComponent: ApplicationComponent,
            authComponent: AuthComponent
        ) : AppComponent
    }

    fun activityComponent(): ActivityComponent
}
