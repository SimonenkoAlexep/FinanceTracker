package com.lsimanenka.financetracker.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

fun scheduleSyncOnConnect(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val request = OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(constraints)
        .addTag("SYNC_ON_CONNECT")
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "SYNC_ON_CONNECT",
            ExistingWorkPolicy.KEEP,
            request
        )
}
