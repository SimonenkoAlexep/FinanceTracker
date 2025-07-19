package com.lsimanenka.financetracker.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.core.content.getSystemService
import javax.inject.Inject

class NetworkMonitor @Inject constructor(
    private val context: Context
) {
    private val cm = context.getSystemService<ConnectivityManager>()!!

    fun startListening(onAvailable: () -> Unit) {
        cm.registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    onAvailable()
                }
            }
        )
    }
}
