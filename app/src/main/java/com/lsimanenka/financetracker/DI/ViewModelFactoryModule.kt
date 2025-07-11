package com.lsimanenka.financetracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds @Singleton
    abstract fun bindFactory(
        factory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}

@Singleton
class DaggerViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel $modelClass")
        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}
