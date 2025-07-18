package com.lsimanenka.financetracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "emoji") val emoji: String,
    @ColumnInfo(name = "is_income") val isIncome: Boolean,
)