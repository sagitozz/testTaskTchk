package com.testtask.testtasktchk.app

import android.content.Context
import com.testtask.testtasktchk.di.AppComponent
import com.testtask.testtasktchk.di.DaggerAppComponent
import com.testtask.testtasktchk.di.app.DaggerApplicationComponentImpl
import com.testtask.testtasktchk.di.googleauth.DaggerGoogleAuthComponentImpl
import com.testtask.testtasktchk.di.users.DaggerUserComponentImpl

/**
 * @autor d.snytko
 */
object AppComponentCreator {

    fun create(context: Context): AppComponent {
        val applicationComponent = DaggerApplicationComponentImpl.factory().create(context)

        return DaggerAppComponent.factory().create(
            applicationComponent = applicationComponent,
            googleAuthComponent = DaggerGoogleAuthComponentImpl.factory().create(applicationComponent),
            userComponent = DaggerUserComponentImpl.factory().create(applicationComponent)
        )
    }
}
