package com.lsimanenka.financetracker.data.DI

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lsimanenka.financetracker.data.network.TransactionsApi
import com.lsimanenka.financetracker.data.repository.TransactionsRepository
import com.lsimanenka.financetracker.data.repository.TransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindTransactionsRepo(
        impl: TransactionsRepositoryImpl
    ): TransactionsRepository
}

