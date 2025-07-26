package com.lsimanenka.financetracker.data

import android.content.Context
import com.lsimanenka.financetracker.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppInfoProvider {

    fun getAppVersion(context: Context): String =
        BuildConfig.VERSION_NAME

    fun getLastUpdateDate(context: Context): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(Date(packageInfo.lastUpdateTime))
    }
}
