package com.testtask.testtasktchk.di.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @autor d.snytko
 */
interface ApplicationComponent {

    val context: Context

    val retrofit: Retrofit
}

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface ApplicationComponentImpl : ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
