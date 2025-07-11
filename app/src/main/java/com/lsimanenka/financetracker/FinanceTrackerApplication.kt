package com.lsimanenka.financetracker

import android.app.Application
import com.lsimanenka.financetracker.di.AppComponent
import com.lsimanenka.financetracker.di.DaggerAppComponent
//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class FinanceTrackerApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}
