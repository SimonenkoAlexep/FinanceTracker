package com.lsimanenka.financetracker.data.repository.categories

import com.lsimanenka.financetracker.data.network.CategoriesApi
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    val api : CategoriesApi
) : CategoriesRepository {
    override suspend fun getCategories(): List<com.lsimanenka.financetracker.data.model.Category> {
        return api.getCategories()
    }

    override suspend fun getCategoriesType(isIncome: Boolean): List<com.lsimanenka.financetracker.data.model.Category> {
        return api.getCategoriesType(isIncome = isIncome)
    }
}