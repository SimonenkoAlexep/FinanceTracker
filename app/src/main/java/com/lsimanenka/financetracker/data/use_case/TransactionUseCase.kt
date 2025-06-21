package com.lsimanenka.financetracker.data.use_case

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.Transaction
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val repository: TransactionsRepository
) {
    operator fun invoke(accountId : Int, startDate : String? = null, endDate : String? = null): Flow<Resource<List<TransactionResponse>>> = flow {
        //try {
            emit(Resource.Loading<List<TransactionResponse>>())
            val transactions = repository.getTransactionsForAccountInPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
            emit(Resource.Success<List<TransactionResponse>>(transactions))
      /*  }catch(e: HttpException) {
            emit(Resource.Error<List<TransactionResponse>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<TransactionResponse>>("Couldn't reach server. Check your internet connection."))
        }*/
    }
}