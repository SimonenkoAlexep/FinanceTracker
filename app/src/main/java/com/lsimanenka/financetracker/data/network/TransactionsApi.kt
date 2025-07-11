package com.lsimanenka.financetracker.data.network

import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsForAccountInPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): List<TransactionResponse>

    @POST("transactions")
    suspend fun createTransaction(@Body request: TransactionRequest): TransactionResponse

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") accountId: Int): TransactionResponse

    @PUT("transactions/{id}")
    suspend fun updateTransactionById(
        @Path("id") accountId: Int,
        @Body request: TransactionRequest
    ): TransactionResponse

    @DELETE("transactions/{id}")
    suspend fun deleteTransactionById(@Path("id") accountId: Int)


}
