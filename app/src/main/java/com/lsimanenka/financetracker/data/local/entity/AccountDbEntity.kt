package com.lsimanenka.financetracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "user_id")     val userId: Long,
    @ColumnInfo(name = "name")        val name: String,
    @ColumnInfo(name = "balance")     val balance: String,
    @ColumnInfo(name = "currency")    val currency: String,
    @ColumnInfo(name = "created_at")  val createdAt: String,
    @ColumnInfo(name = "updated_at")  val updatedAt: String
)