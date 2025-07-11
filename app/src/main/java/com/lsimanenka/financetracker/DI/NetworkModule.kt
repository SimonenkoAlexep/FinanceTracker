package com.lsimanenka.financetracker.di

import com.lsimanenka.financetracker.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lsimanenka.financetracker.common.Constants.BASE_URL
import com.lsimanenka.financetracker.data.network.AccountApi
import com.lsimanenka.financetracker.data.network.CategoriesApi
import com.lsimanenka.financetracker.data.network.TransactionsApi
import dagger.Module
import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
//@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Add your bearer token
    //private const val BEARER_TOKEN = "Bearer 6R9ucF6hNbBS4WwKBannqbQk"
    private const val apiToken = BuildConfig.API_TOKEN

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", apiToken)
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor
    ): OkHttpClient {
        // interceptor для логов
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)    // 1) твой auth
            .addInterceptor(logging)            // 2) логирование HTTP
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson : Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideTransactionsApi(retrofit: Retrofit): TransactionsApi =
        retrofit.create(TransactionsApi::class.java)

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)
}