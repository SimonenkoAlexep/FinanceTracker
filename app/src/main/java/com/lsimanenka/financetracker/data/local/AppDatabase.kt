package com.lsimanenka.financetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lsimanenka.financetracker.data.local.dao.AccountDao
import com.lsimanenka.financetracker.data.local.dao.CategoryDao
import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.TransactionDbEntity
import com.lsimanenka.financetracker.data.local.entity.CategoryDbEntity
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionWithDetails

@Database(
    version = 4,
    entities = [
        AccountDbEntity::class,
        CategoryDbEntity::class,
        TransactionDbEntity::class,
        StatItemDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao

    abstract fun getCategoryDao(): CategoryDao

}