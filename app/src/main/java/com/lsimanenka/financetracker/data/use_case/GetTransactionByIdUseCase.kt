package com.lsimanenka.financetracker.data.use_case

import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.repository.transaction.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    operator fun invoke(accountId: Int) : Flow<Resource<TransactionResponse>> = flow {
        try {
            emit(Resource.Loading())
            val transaction = repository.getTransactionById(accountId)
            emit(Resource.Success(transaction))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ошибка"))
        } catch(e: IOException) {
            emit(Resource.Error("Нет соединения"))
        }
    }
}