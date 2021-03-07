package com.testtask.testtasktchk.app

import android.app.Application
import com.testtask.testtasktchk.di.AppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponentCreator.create(this)
    }

    companion object {

        lateinit var appComponent: AppComponent
    }
}
