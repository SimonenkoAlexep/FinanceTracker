package com.lsimanenka.financetracker.data.use_case

import android.util.Log
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.Category
import com.lsimanenka.financetracker.data.repository.categories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = repository.getCategories()
            //Log.d("CATEGORIES" ,"${categories.first().name}")
            emit(Resource.Success(categories))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ошибка"))
        } catch(e: IOException) {
            emit(Resource.Error("Нет соединения"))
        }
    }
}
