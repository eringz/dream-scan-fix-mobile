package com.example.dreamscanfix.data.remote

interface ProductApiService {
    suspend fun fetchFromApi(query: String): List<ProductDto>
    suspend fun resolveBarcode(barcode: String): ProductDto
    suspend fun searchByText(query: String): List<ProductDto>
}


