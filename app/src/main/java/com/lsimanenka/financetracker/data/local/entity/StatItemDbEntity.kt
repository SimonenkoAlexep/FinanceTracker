package com.lsimanenka.financetracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stat_item")
data class StatItemDbEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "emoji") val emoji: String,
    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "type") val type: String
)