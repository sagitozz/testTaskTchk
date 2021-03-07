package com.testtask.testtasktchk.di.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component

/**
 * @autor d.snytko
 */
@Component
interface ApplicationComponent {

    val context: Context

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
