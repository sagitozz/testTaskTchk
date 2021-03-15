package com.testtask.testtasktchk.di.users

import com.testtask.testtasktchk.data.repository.UsersRepository
import com.testtask.testtasktchk.di.app.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

/**
 * @autor d.snytko
 */
interface UserComponent {

    val repository: UsersRepository
}

@Singleton
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [UserModule::class]
)
interface UserComponentImpl : UserComponent {

    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): UserComponent
    }
}
