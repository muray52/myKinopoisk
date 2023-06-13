package com.vostrikov.mykinopoisk.di

import android.app.Application
import com.vostrikov.mykinopoisk.data.api.ApiFactory
import com.vostrikov.mykinopoisk.data.api.ApiService
import com.vostrikov.mykinopoisk.data.db.AppDatabase
import com.vostrikov.mykinopoisk.data.db.FilmsDao
import com.vostrikov.mykinopoisk.data.repository.FilmsRepositoryImpl
import com.vostrikov.mykinopoisk.data.sharedpreferences.SharedPref
import com.vostrikov.mykinopoisk.domain.FilmsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindFilmsRepository(impl: FilmsRepositoryImpl): FilmsRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideFilmsDao(application: Application): FilmsDao {
            return AppDatabase.getInstance(application).filmsDao()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
         fun provideSharedPref(application: Application): SharedPref {
            return SharedPref.createInstance(application)
        }
    }
}