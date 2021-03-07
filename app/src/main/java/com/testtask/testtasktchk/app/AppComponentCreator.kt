package com.testtask.testtasktchk.app

import android.content.Context
import com.testtask.testtasktchk.di.AppComponent
import com.testtask.testtasktchk.di.DaggerAppComponent
import com.testtask.testtasktchk.di.app.DaggerApplicationComponent
import com.testtask.testtasktchk.di.auth.DaggerAuthComponent

/**
 * @autor d.snytko
 */
object AppComponentCreator {

    fun create(context: Context): AppComponent {
        val applicationComponent = DaggerApplicationComponent.factory().create(context)

        return DaggerAppComponent.factory().create(
            applicationComponent = applicationComponent,
            authComponent = DaggerAuthComponent.factory().create(applicationComponent)
        )
    }
}
