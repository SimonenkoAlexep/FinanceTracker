package com.lsimanenka.financetracker.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    private val context: Context
) {
    fun isOnline(): Boolean {
        val cm = context.getSystemService<ConnectivityManager>()
            ?: return false

        val network = cm.activeNetwork
            ?: return false

        val capabilities = cm.getNetworkCapabilities(network)
            ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
