package com.example.dreamscanfix.domain.use_case

import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.repository.ProductRepository

class SaveProductUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        if (product.title.isNotBlank()) {
            repository.saveProduct(product)
        }
    }
}

