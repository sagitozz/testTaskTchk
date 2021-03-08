package com.testtask.testtasktchk.di.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @autor d.snytko
 */
@Component(
    modules = [NetworkModule::class]
)
interface ApplicationComponent {

    val context: Context

    val retrofit: Retrofit

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
