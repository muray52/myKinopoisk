package com.vostrikov.mykinopoisk.di

import androidx.lifecycle.ViewModel
import com.vostrikov.mykinopoisk.presentation.detailedinfo.DetailedInfoViewModel
import com.vostrikov.mykinopoisk.presentation.list_films.ListFilmsViewModel
import com.vostrikov.mykinopoisk.presentation.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(impl: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListFilmsViewModel::class)
    fun bindListFilmsModel(impl: ListFilmsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailedInfoViewModel::class)
    fun bindDetailedInfoViewModel(impl: DetailedInfoViewModel): ViewModel
}