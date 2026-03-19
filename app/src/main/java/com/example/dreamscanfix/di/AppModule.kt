package com.example.dreamscanfix.di

import com.example.dreamscanfix.data.local.ProductDao
import com.example.dreamscanfix.data.remote.ProductApiService
import com.example.dreamscanfix.data.remote.ProductDto
import com.example.dreamscanfix.data.remote.ScraperEngine
import com.example.dreamscanfix.data.remote.ScraperEngineImpl
import com.example.dreamscanfix.data.remote.VisionProcessor
import com.example.dreamscanfix.data.repository.ProductRepositoryImpl
import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.repository.ProductRepository
import com.example.dreamscanfix.domain.use_case.SearchProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideScraperEngine(): ScraperEngine {
        return ScraperEngineImpl()
    }

    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService {
        return object : ProductApiService {
            override suspend fun fetchFromApi(query: String): List<ProductDto> = emptyList()
            override suspend fun resolveBarcode(barcode: String): ProductDto = ProductDto("", 0.0, "", "")
            override suspend fun searchByText(query: String): List<ProductDto> = emptyList()
        }
    }

    @Provides
    @Singleton
    fun provideVisionProcessor(): VisionProcessor {
        return object : VisionProcessor {
            override suspend fun identifyObject(imageUrl: String): String? = null
        }
    }

    @Provides
    @Singleton
    fun provideProductDao(): ProductDao {
        return object : ProductDao {
            override fun getAllProducts(): Flow<List<Product>> = flowOf(emptyList())
            override suspend fun getProductById(id: String): Product? = null
            override suspend fun insertProduct(product: Product) {}
            override suspend fun updateProduct(product: Product) {}
            override suspend fun deleteProduct(product: Product) {}
            override fun getRecentQueries(): Flow<List<String>> = flowOf(emptyList())
            override suspend fun insertQuery(query: String) {}
        }
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ProductApiService,
        scraperEngine: ScraperEngine,
        visionProcessor: VisionProcessor,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepositoryImpl(
            apiService = apiService,
            scraperEngine = scraperEngine,
            visionProcessor = visionProcessor,
            productDao = productDao
        )
    }

    @Provides
    @Singleton
    fun provideSearchProductUseCase(repository: ProductRepository): SearchProductUseCase {
        return SearchProductUseCase(repository)
    }
}
