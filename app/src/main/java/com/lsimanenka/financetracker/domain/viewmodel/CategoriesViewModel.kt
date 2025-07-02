package com.lsimanenka.financetracker.domain.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.Category
import com.lsimanenka.financetracker.data.use_case.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Category>? = null,
    val error: String = ""
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CategoriesState())
    val state: State<CategoriesState> = _state

    init {
        getCategoriesUseCase()
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Loading -> CategoriesState(isLoading = true)
                    is Resource.Success -> CategoriesState(categories = result.data)
                    is Resource.Error   -> CategoriesState(error = result.message.orEmpty())
                }
            }
            .launchIn(viewModelScope)
    }
}
