package com.lsimanenka.financetracker.di

import androidx.work.WorkerFactory
import com.lsimanenka.financetracker.data.worker.DaggerWorkerFactory
import com.lsimanenka.financetracker.data.worker.SyncWorkerFactory
import com.lsimanenka.financetracker.data.worker.SyncWorkerFactoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class WorkerModule {
    @Binds abstract fun bindWorkerFactory(
        factory: DaggerWorkerFactory
    ): WorkerFactory
    @Binds abstract fun bindSyncWorkerFactory(
        impl: SyncWorkerFactoryImpl
    ): SyncWorkerFactory
}