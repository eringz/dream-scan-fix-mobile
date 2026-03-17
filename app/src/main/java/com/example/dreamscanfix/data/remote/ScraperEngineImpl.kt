package com.example.dreamscanfix.data.remote

import android.R.attr.src
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import java.net.URLEncoder

class ScraperEngineImpl: ScraperEngine {
    override suspend fun executeParallelScrape(query: String): List<ProductDto> = coroutineScope {

        // L1: Jsoup Scrapping
        val staticSearch = async(Dispatchers.IO) { scrapeStaticWebSource(query) }

        val dynamicSearch = async(Dispatchers.IO) { scrapeDynamicWebSource(query) }


        staticSearch.await() + dynamicSearch.await()


        staticSearch.await()
    }

    private fun scrapeStaticWebSource(query: String): List<ProductDto> {
        return try {
            val encodeQuery = URLEncoder.encode(query, "UTF-8")
            val targetUrl = "https://www.google.com/search?q=$encodeQuery&tbm=shop"

            val document = Jsoup.connect(targetUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(10000)
                .get()


            document.select("div.sh-dgr__content").map {
                productElement ->
                val title = productElement.select("div.sh-np__product-title").text()
                val priceRaw = productElement.select("span.a8Pemb").text()
                val parsedPrice = priceRaw.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: 0.0

                ProductDto(
                    title = title,
                    price = parsedPrice,
                    imageUrl = productElement.select("img").attr("src"),
                    shopUrl = "https://www.google.com" + productElement.select("a").attr("href")
                )
            }.filter { it.title.isNotEmpty() }
            emptyList<ProductDto>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun scrapeDynamicWebSource(query: String): List<ProductDto> {
        // TODO: Playwright Headless proxy coming soon

        return emptyList()
    }
}