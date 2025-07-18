package com.lsimanenka.financetracker

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lsimanenka.financetracker.di.AppComponent
import com.lsimanenka.financetracker.di.DaggerAppComponent
import javax.inject.Inject
import androidx.work.WorkerFactory
import com.lsimanenka.financetracker.data.NetworkMonitor
import com.lsimanenka.financetracker.data.worker.SyncWorker
import com.lsimanenka.financetracker.data.worker.scheduleSyncOnConnect

class FinanceTrackerApplication : Application(), Configuration.Provider {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
        appComponent.inject(this)

        WorkManager.initialize(
            this,
            workManagerConfiguration
        )

        networkMonitor.startListening {
            scheduleSyncOnConnect(this)
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
