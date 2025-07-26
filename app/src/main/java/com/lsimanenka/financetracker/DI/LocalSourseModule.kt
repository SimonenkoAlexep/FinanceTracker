package com.lsimanenka.financetracker.di

import com.lsimanenka.financetracker.data.repository.account.AccountLocalDataSource
import com.lsimanenka.financetracker.data.local.dao.AccountDao
import com.lsimanenka.financetracker.data.local.dao.CategoryDao
import com.lsimanenka.financetracker.data.local.dao.TransactionDao
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.repository.categories.CategoriesLocalDataSource
import com.lsimanenka.financetracker.data.repository.transaction.TransactionLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalSourceModule {

    @Provides
    @Singleton
    fun provideAccountLocalDataSource(
        dao: AccountDao
    ): AccountLocalDataSource = AccountLocalDataSource(dao)

    @Provides
    @Singleton
    fun provideCategoriesLocalDataSource(
        dao: CategoryDao
    ): CategoriesLocalDataSource = CategoriesLocalDataSource(dao)

    @Provides
    @Singleton
    fun provideTransactionLocalDataSource(
        dao: TransactionDao
    ): TransactionLocalDataSource = TransactionLocalDataSource(dao)
}