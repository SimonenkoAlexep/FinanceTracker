package com.lsimanenka.financetracker.di

import com.lsimanenka.financetracker.data.repository.account.AccountLocalDataSource
import com.lsimanenka.financetracker.data.local.dao.AccountDao
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
}