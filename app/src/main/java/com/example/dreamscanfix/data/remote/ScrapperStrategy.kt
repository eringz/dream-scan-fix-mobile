package com.example.dreamscanfix.data.remote

interface ScrapperStrategy {
    suspend fun search(query: String): List<ProductDto>
}