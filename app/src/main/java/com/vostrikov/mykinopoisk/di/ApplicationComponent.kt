package com.vostrikov.mykinopoisk.di

import android.app.Application
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoFragment
import com.vostrikov.mykinopoisk.presentation.list_films.ListFilmsActivity
import com.vostrikov.mykinopoisk.presentation.login.LoginActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: Application)

    fun inject(activity: LoginActivity)

    fun inject(activity: ListFilmsActivity)

    fun inject(fragment: DetailedInfoFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}