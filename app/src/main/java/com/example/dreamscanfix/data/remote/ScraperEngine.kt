package com.example.dreamscanfix.data.remote

interface ScraperEngine {
    suspend fun executeParallelScrape(query: String): List<ProductDto>
}