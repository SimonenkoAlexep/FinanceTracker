package com.lsimanenka.financetracker.data.network

import com.lsimanenka.financetracker.data.model.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsForAccountInPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): List<TransactionResponse>

    /*@POST("transactions")
    suspend fun create(@Body request: TransactionRequest): TransactionResponse*/




}
