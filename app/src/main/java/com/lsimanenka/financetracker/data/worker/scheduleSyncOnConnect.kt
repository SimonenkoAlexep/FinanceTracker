package com.lsimanenka.financetracker.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleSyncOnConnect(context: Context, intervalHours: Int) {

    val request = PeriodicWorkRequestBuilder<SyncWorker>(intervalHours.toLong(), TimeUnit.HOURS)
        .addTag("SYNC_ON_CONNECT")
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "SYNC_ON_CONNECT",
        ExistingPeriodicWorkPolicy.UPDATE,
        request
    )


    /*val request = OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        )
        .addTag("SYNC_ON_CONNECT")
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "SYNC_ON_CONNECT",
            ExistingWorkPolicy.KEEP,
            request
        )*/
}
