package com.example.dreamscanfix.domain.repository

import com.example.dreamscanfix.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    // Scanning and Web Scrapping
    suspend fun searchProductByBarcode(barcode: String): Result<List<Product>>
    suspend fun searchProductByObject(imageUrl: String): Result<List<Product>>
    suspend fun searchProductByManual(query: String): Result<List<Product>>

    // Inventory
    fun getInventory(): Flow<List<Product>>
    suspend fun getProductById(id: String): Product?
    suspend fun saveProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)

    // History
    fun getSearchHistory(): Flow<List<String>>
    suspend fun saveSearchHistory(query: String)

}