package com.example.dreamscanfix.domain.use_case

import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.model.SearchType
import com.example.dreamscanfix.domain.repository.ProductRepository

class SearchProductUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String, type: SearchType): Result<List<Product>> {
        if (query.isBlank()) {
            val errorMessage = when (type) {
                SearchType.BARCODE -> "Barcode is Empty"
                SearchType.OBJECT -> "Image URL is Empty"
                SearchType.MANUAL -> "Search query is Empty"
            }
            return Result.failure(IllegalArgumentException(errorMessage))
        }



        return when (type) {
            SearchType.BARCODE -> repository.searchProductByBarcode(query)
            SearchType.OBJECT -> repository.searchProductByObject(query)
            SearchType.MANUAL -> repository.searchProductByManual(query)
        }
    }
}