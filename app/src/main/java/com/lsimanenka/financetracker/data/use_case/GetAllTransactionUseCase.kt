package com.lsimanenka.financetracker.data.use_case

import android.annotation.SuppressLint
import android.util.Log
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.repository.transaction.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetAllTransactionUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    @SuppressLint("NewApi")
    operator fun invoke(accountId : Int, startDate : String? = null, endDate : String? = null): Flow<Resource<List<TransactionResponse>>> = flow {
        try {
            emit(Resource.Loading<List<TransactionResponse>>())
            val fmt = DateTimeFormatter.ISO_LOCAL_DATE
            val transactions = repository.getTransactionsForAccountInPeriod(
                accountId = accountId,
                startDate = LocalDate.now().minusDays(29).format(fmt),
                endDate = LocalDate.now().format(fmt)
            )
            Log.d("CHART", "${transactions.first().transactionDate}")
            emit(Resource.Success<List<TransactionResponse>>(transactions))
        } catch(e: HttpException) {
            emit(Resource.Error<List<TransactionResponse>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<TransactionResponse>>("Couldn't reach server. Check your internet connection."))
        }
    }
}