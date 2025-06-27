package com.lsimanenka.financetracker.data.network

import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountCreateRequest
import com.lsimanenka.financetracker.data.model.AccountHistoryResponse
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {
    @GET("accounts")
    suspend fun getAccounts(): List<Account>

    @POST("accounts")
    suspend fun createAccount(
        @Body request: AccountCreateRequest
    ): AccountResponse

    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") accountId: Int
    ): AccountResponse

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") accountId: Int,
        @Body request: AccountUpdateRequest
    ): Account

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(
        @Path("id") accountId: Int
    ): Response

    @GET("accounts/{id}/history")
    suspend fun getAccountByIdHistory(
        @Path("id") accountId: Int
    ): AccountHistoryResponse
}