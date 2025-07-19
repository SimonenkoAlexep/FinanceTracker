package com.lsimanenka.financetracker.di

import android.content.Context
import androidx.room.Room
import com.lsimanenka.financetracker.data.local.AppDatabase
import com.lsimanenka.financetracker.data.local.dao.AccountDao
import com.lsimanenka.financetracker.data.local.dao.CategoryDao
import com.lsimanenka.financetracker.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "finance-tracker.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides @Singleton
    fun provideAccountDao(db: AppDatabase): AccountDao =
        db.getAccountDao()

    @Provides @Singleton
    fun provideCategoryDao(db: AppDatabase): CategoryDao =
        db.getCategoryDao()

    @Provides @Singleton
    fun provideTransactionDao(db: AppDatabase): TransactionDao =
        db.getTransactionDao()
}
