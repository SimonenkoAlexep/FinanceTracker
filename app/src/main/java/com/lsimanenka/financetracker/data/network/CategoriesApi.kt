package com.lsimanenka.financetracker.data.network

import com.lsimanenka.financetracker.data.model.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApi {
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesType(
        @Path("isIncome") isIncome: Boolean
    ): List<Category>
}

