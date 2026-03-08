package com.example.dreamscanfix.domain.model

import org.junit.Assert.*
import org.junit.Test
import org.junit.Assert.assertNotEquals


class ProductTest {

    // TEST FOR UNIQUE ID
    @Test
    fun `product should generate unique ids when instantiated`() {
        val product1 = Product(title = "Item 1")
        val product2 = Product(title ="Item 2")

        assertNotEquals(product1.id, product2.id)

    }

    // test for correct data
    @Test
    fun `product should hold correct canvassing data`() {
        val testTitle = "Mechanical Keyboard"
        val testPrice = 2500.0
        val testShopUrl = "https://shopee.ph/sample-item"

        val product = Product(
            title = testTitle,
            price = testPrice,
            shopUrl = testShopUrl,
            source = SearchType.BARCODE
        )

        assertEquals(testTitle, product.title)
        assertEquals(testPrice, product.price, 0.0)
        assertEquals(testShopUrl, product.shopUrl)
        assertEquals(SearchType.BARCODE, product.source)

    }

    // TEST FOR VALUES ALLOWED IN PRICE
    @Test
    fun `product price should be in positive value`() {
        val product = Product(title = "Invalid Item", price = 5.0)

        assertTrue("Price should be zero or positive",product.price >= 0.0)
    }

    // TEST FOR DEFAULT TIMESTAMPS
    @Test
    fun `product should have valid default timestamps`() {
        val currentTime = System.currentTimeMillis()
        val product = Product(title = "Test Product")

        assertTrue(product.createdAt <= currentTime + 1000)
        assertTrue(product.updatedAt <= currentTime + 1000)
    }

    @Test
    fun `copy should update price and updatedAt while keeping other fields intact`() {
        val original = Product(title = "Coffee", price = 50.0)
        val newTime = System.currentTimeMillis() + 5000

        // Gamit ang .copy(), isang field lang ang babaguhin natin
        val updated = original.copy(price = 55.0, updatedAt = newTime)

        assertEquals(original.id, updated.id) // Dapat same ID pa rin
        assertEquals(55.0, updated.price, 0.0) // New price
        assertEquals(newTime, updated.updatedAt) // New timestamp
        assertEquals(original.createdAt, updated.createdAt) // Original creation date
    }


}