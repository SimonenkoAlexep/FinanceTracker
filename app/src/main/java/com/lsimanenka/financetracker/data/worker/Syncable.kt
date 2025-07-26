package com.lsimanenka.financetracker.data.worker

interface Syncable {
    suspend fun sync()
}
