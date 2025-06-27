package com.lsimanenka.financetracker.data.use_case

import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<Resource<List<Account>>> = flow {
        try {
            emit(Resource.Loading())
            val accounts = repository.getAccounts()
            emit(Resource.Success(accounts))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ошибка"))
        } catch(e: IOException) {
            emit(Resource.Error("Нет соединения"))
        }
    }
}
