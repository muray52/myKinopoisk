package com.vostrikov.mykinopoisk

import android.app.Application
import com.vostrikov.mykinopoisk.di.DaggerApplicationComponent

class ApplicationMyKinopoisk : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}