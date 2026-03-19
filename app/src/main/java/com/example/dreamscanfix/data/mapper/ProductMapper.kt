package com.example.dreamscanfix.data.mapper

import com.example.dreamscanfix.data.remote.ProductDto
import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.model.SearchType

fun ProductDto.toDomain(type: SearchType = SearchType.MANUAL): Product {
    return Product(
        title = this.title,
        price = this.price,
        imageUrl = this.imageUrl,
        shopUrl = this.shopUrl,
        source = type
    )
}