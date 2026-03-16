package com.example.dreamscanfix.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import java.net.URLEncoder

class ScraperEngineImpl: ScraperEngine {
    override suspend fun executeParallelScrape(query: String): List<ProductDto> = coroutineScope {

        // L1: Jsoup Scrapping
        val staticSearch = async(Dispatchers.IO) { scrapeStaticWebSource(query) }

        staticSearch.await()
    }

    private fun scrapeStaticWebSource(query: String): List<ProductDto> {
        return try {
            val encodeQuery = URLEncoder.encode(query, "UTF-8")
            val targetUrl = "https://www.google.com/search?q=$encodeQuery&tbm=shop"

            val document = Jsoup.connect(targetUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .get()

            // TODO: Parse document to extract products
            emptyList<ProductDto>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}