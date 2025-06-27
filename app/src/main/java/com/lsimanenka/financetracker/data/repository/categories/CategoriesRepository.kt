package com.lsimanenka.financetracker.data.repository.categories

import com.lsimanenka.financetracker.data.model.Category


interface CategoriesRepository {
    suspend fun getCategories(): List<Category>

    suspend fun getCategoriesType(
        isIncome: Boolean
    ): List<Category>
}