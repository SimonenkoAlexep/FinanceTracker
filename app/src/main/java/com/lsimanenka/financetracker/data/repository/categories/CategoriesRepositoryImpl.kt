package com.lsimanenka.financetracker.data.repository.categories

import com.lsimanenka.financetracker.data.NetworkChecker
import com.lsimanenka.financetracker.data.mappers.toAccount
import com.lsimanenka.financetracker.data.mappers.toCategory
import com.lsimanenka.financetracker.data.mappers.toDbEntity
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.Category
import com.lsimanenka.financetracker.data.network.CategoriesApi
import com.lsimanenka.financetracker.data.repository.account.AccountLocalDataSource
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    val api : CategoriesApi,
    private val local: CategoriesLocalDataSource,
    private val networkChecker: NetworkChecker
) : CategoriesRepository {
    override suspend fun getCategories(): List<Category> {
        return if (networkChecker.isOnline()) {
            val remoteList = api.getCategories()

            remoteList.forEach { resp ->
                val categoryEntity = resp.toDbEntity()
                local.upsertCategory(categoryEntity)
            }
            remoteList
        } else {
            local.getCategories().map { accounts ->
                accounts.toCategory()
            }
        }
    }

    override suspend fun getCategoriesType(isIncome: Boolean): List<Category> {
        return api.getCategoriesType(isIncome = isIncome)
    }
}