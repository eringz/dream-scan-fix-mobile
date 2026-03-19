package com.example.dreamscanfix.domain.model

import java.util.UUID

data class Product (
    val id: String = UUID.randomUUID().toString(),
    val barcode: String? = null,
    val title: String,
    val price: Double? = 0.0,
    val currency: String = "PHP",
    val imageUrl: String? = null,
    val shopUrl: String? = null,
    val source: SearchType = SearchType.MANUAL,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    init {
        require(title.isNotBlank()) { "Title cannot be blank" }
        if (price != null) {
            require(price >= 0) { "Price must be a positive value"}
        }
    }
}

enum class SearchType {
    BARCODE, OBJECT, MANUAL
}

