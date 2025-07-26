package com.lsimanenka.financetracker.di

import com.lsimanenka.financetracker.data.worker.AccountSyncable
import com.lsimanenka.financetracker.data.worker.Syncable
import com.lsimanenka.financetracker.data.worker.TransactionSyncable
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class SyncModule {
    @Binds
    @IntoSet
    abstract fun bindAccountSync(ab: AccountSyncable): Syncable
    @Binds
    @IntoSet
    abstract fun bindTxnSync(tx: TransactionSyncable): Syncable
}