package com.example.dreamscanfix.data.local

import com.example.dreamscanfix.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDao {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun getProductById(id: String): Product?
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    fun getRecentQueries(): Flow<List<String>>
    suspend fun insertQuery(query: String)
}


