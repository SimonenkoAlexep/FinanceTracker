package com.lsimanenka.financetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.CategoryDbEntity
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category\n")
    suspend fun getCategories(): List<CategoryDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(categoryDbEntity: CategoryDbEntity)

}