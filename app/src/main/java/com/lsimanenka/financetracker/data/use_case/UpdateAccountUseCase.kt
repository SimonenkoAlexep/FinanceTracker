package com.lsimanenka.financetracker.data.use_case

import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(accountId: Int, request: AccountUpdateRequest): Flow<Resource<Account>> = flow {
        try {
            emit(Resource.Loading())
            val account = repository.updateAccountById(accountId = accountId, request = request)
            emit(Resource.Success(account))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ошибка"))
        } catch(e: IOException) {
            emit(Resource.Error("Нет соединения"))
        }
    }
}
