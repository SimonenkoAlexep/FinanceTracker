package com.lsimanenka.financetracker.data.DI

import com.lsimanenka.financetracker.data.repository.account.AccountRepository
import com.lsimanenka.financetracker.data.repository.account.AccountRepositoryImpl
import com.lsimanenka.financetracker.data.repository.categories.CategoriesRepository
import com.lsimanenka.financetracker.data.repository.categories.CategoriesRepositoryImpl
import com.lsimanenka.financetracker.data.repository.transaction.TransactionsRepository
import com.lsimanenka.financetracker.data.repository.transaction.TransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindTransactionsRepo(
        impl: TransactionsRepositoryImpl
    ): TransactionsRepository

    @Binds @Singleton
    abstract fun bindAccountRepo(
        impl: AccountRepositoryImpl
    ): AccountRepository

    @Binds @Singleton
    abstract fun bindCategoriesRepo(
        impl: CategoriesRepositoryImpl
    ): CategoriesRepository

}

