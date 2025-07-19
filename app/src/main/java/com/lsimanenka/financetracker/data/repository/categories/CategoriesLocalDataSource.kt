package com.lsimanenka.financetracker.data.repository.categories

import android.annotation.SuppressLint
import android.util.Log
import com.lsimanenka.financetracker.data.local.dao.AccountDao
import com.lsimanenka.financetracker.data.local.dao.CategoryDao
import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.CategoryDbEntity
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.mappers.toAccount
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import java.time.Instant
import javax.inject.Inject


class CategoriesLocalDataSource @Inject constructor(
    private val dao: CategoryDao
) {
    suspend fun getCategories(): List<CategoryDbEntity> =
        dao.getCategories()

    suspend fun upsertCategory(entity: CategoryDbEntity) {
        dao.upsertCategory(entity)
    }
}
