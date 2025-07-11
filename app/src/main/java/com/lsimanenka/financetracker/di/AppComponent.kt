package com.lsimanenka.financetracker.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelBindingModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {
    fun viewModelFactory(): javax.inject.Provider<androidx.lifecycle.ViewModelProvider.Factory>
}