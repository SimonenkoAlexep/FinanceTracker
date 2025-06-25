package com.lsimanenka.financetracker.data.DI

/*import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lsimanenka.financetracker.common.Constants
import com.lsimanenka.financetracker.common.Constants.BASE_URL
import com.lsimanenka.financetracker.data.network.TransactionsApi
import com.lsimanenka.financetracker.data.repository.TransactionsRepository
import com.lsimanenka.financetracker.data.repository.TransactionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory

/*
// NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit =
//        Retrofit.Builder()
//            .baseUrl("https://shmr-finance.ru")
//            .addConverterFactory(
//                Json { ignoreUnknownKeys = true }
//                    .asConverterFactory("application/json".toMediaType())
//            )
//            .build()

    @Provides
    @Singleton
    fun provideTransactionsRepository(api: TransactionsApi): TransactionsRepository {
        return TransactionsRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideTransactionsApi(): TransactionsApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TransactionsApi::class.java)
    }
}
*/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://BASE_URL")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides @Singleton
    fun provideTransactionsApi(retrofit: Retrofit): TransactionsApi =
        retrofit.create(TransactionsApi::class.java)
}
*/

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lsimanenka.financetracker.common.Constants.BASE_URL
import com.lsimanenka.financetracker.data.network.TransactionsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BEARER_TOKEN = "Bearer 6R9ucF6hNbBS4WwKBannqbQk"

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", BEARER_TOKEN)
            .build()
        chain.proceed(newRequest)
    }

    /*@Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return AndroidNetworkMonitor(context)
    }

    @Provides
    @Singleton
    fun provideApiCallHelper(networkMonitor: NetworkMonitor): ApiCallHelper {
        return ApiCallHelper(networkMonitor)
    }*/

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

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
}