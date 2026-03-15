package com.example.dreamscanfix.data.repository

import com.example.dreamscanfix.data.local.ProductDao
import com.example.dreamscanfix.data.mapper.toDomain
import com.example.dreamscanfix.data.remote.ProductApiService
import com.example.dreamscanfix.data.remote.ScraperEngine
import com.example.dreamscanfix.data.remote.VisionProcessor
import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

import com.example.dreamscanfix.domain.model.SearchType
import kotlinx.coroutines.flow.map
import kotlin.collections.map


class ProductRepositoryImpl (
    private val apiService: ProductApiService,
    private val scraperEngine: ScraperEngine,
    private val visionProcessor: VisionProcessor,
    private val productDao: ProductDao

) : ProductRepository {
    override suspend fun searchProductByBarcode(barcode: String): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchProductByObject(imageUrl: String): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchProductByManual(query: String): Result<List<Product>> {
        return try {
            val apiResults = apiService.searchByText(query)
            val scrapedResults = scraperEngine.executeParallelScrape(query)

            val combined = (apiResults + scrapedResults).map { it.toDomain() }
            Result.success(combined.sortedBy { it.price })
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException) throw e
            Result.failure(e)
        }
    }

    override fun getInventory(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: String): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun saveProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override fun getSearchHistory(): Flow<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveSearchHistory(query: String) {
        TODO("Not yet implemented")
    }
}