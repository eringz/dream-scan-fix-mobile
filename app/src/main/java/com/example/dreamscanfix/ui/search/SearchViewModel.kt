package com.example.dreamscanfix.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.model.SearchType
import com.example.dreamscanfix.domain.use_case.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductUseCase: SearchProductUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String, type: SearchType = SearchType.MANUAL) {
        if (query.isBlank()) return

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                products = emptyList(),
                error = null
            )

            val result = searchProductUseCase(query, type)

            result.onSuccess {
                list ->
                _state.value = _state.value.copy(
                    products = list,
                    isLoading = false,
                )
            }.onFailure {
                exception ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class SearchState (
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
