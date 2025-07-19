package com.lsimanenka.financetracker.di

import android.app.Application
import android.content.Context
import com.lsimanenka.financetracker.FinanceTrackerApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelBindingModule::class,
        ViewModelFactoryModule::class,
        WorkerModule::class,
        DatabaseModule::class,
        LocalSourceModule::class,
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(ctx: Context): Builder

        fun build(): AppComponent
    }

    fun viewModelFactory(): javax.inject.Provider<androidx.lifecycle.ViewModelProvider.Factory>
    fun inject(app: FinanceTrackerApplication)
}