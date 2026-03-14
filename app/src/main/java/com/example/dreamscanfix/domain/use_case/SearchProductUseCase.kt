package com.example.dreamscanfix.domain.use_case

import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.repository.ProductRepository

class SearchProductUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(barcode: String): Result<List<Product>> {
        if (barcode.isBlank()) return Result.failure(Exception("Barcode is Empty"))

        return repository.searchProductByBarcode(barcode)
    }
}