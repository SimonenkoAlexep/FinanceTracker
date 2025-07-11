package com.lsimanenka.financetracker.data.use_case

import android.util.Log
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.repository.transaction.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostTransactionUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    operator fun invoke(request: TransactionRequest) : Flow<Resource<TransactionResponse>> = flow {
        try {
            emit(Resource.Loading())
            val transaction = repository.createTransaction(request)
            //Log.d("repository", "${transaction.category.name}")
            emit(Resource.Success(transaction))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ошибка"))
        } catch(e: IOException) {
            emit(Resource.Error("Нет соединения"))
        }
    }
}