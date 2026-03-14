package com.example.dreamscanfix.domain.use_case

import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.model.SearchType
import com.example.dreamscanfix.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertTrue

class SearchProductUseCaseTest {
    private val repository: ProductRepository = mockk(relaxed = true)
    private val searchProductUseCase = SearchProductUseCase(repository)

    @Test
    fun `should return failure when barcode is empty by barcode`() = runTest {
        val result = searchProductUseCase("", SearchType.BARCODE)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message == "Barcode is Empty")

        coVerify(exactly = 0) { repository.searchProductByBarcode(any()) }
    }

    @Test
    fun `should return success list when repository finds products by barcode`() = runTest {
        val testBarcode = "1234567890"
        val mockProducts = listOf(
            Product(title = "Mechanical Keyboard", price = 2500.0, barcode = testBarcode),
        )

        coEvery { repository.searchProductByBarcode(testBarcode) } returns Result.success(mockProducts)

        val result = searchProductUseCase(testBarcode, SearchType.BARCODE)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == mockProducts)

        coVerify { repository.searchProductByBarcode(testBarcode) }
    }

    @Test
    fun `should return failure when object is empty`() = runTest {
        val result = searchProductUseCase("", SearchType.OBJECT)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message == "Image URL is Empty")

        coVerify(exactly = 0) { repository.searchProductByObject(any())}
    }

    @Test
    fun `should return success list when repository finds products by object detection`() = runTest {
        val testImage = "https://media.wired.com/photos/68a91be7bbceeadd076b1720/master/w_1600%2Cc_limit/logitech-mx-keys-s-keyboard-Reviewer-Photo-SOURCE-Eric-Ravenscraft.png"
        val mockProducts = listOf(
            Product(title = "Mechanical Keyboard", price = 2500.0, imageUrl = testImage),
        )

        coEvery { repository.searchProductByObject(testImage) } returns Result.success(mockProducts)

        val result = searchProductUseCase(testImage, SearchType.OBJECT)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == mockProducts)

        coVerify { repository.searchProductByObject(testImage) }

    }


    @Test
    fun `should return failure when manual query is empty`() = runTest {
        val result = searchProductUseCase("", SearchType.MANUAL)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message == "Search query is Empty")

        coVerify(exactly = 0) { repository.searchProductByManual(any()) }
    }

    @Test
    fun `should return success list when repository finds products by manual search`() = runTest {
        val testQuery = "Mechanical Keyboard"
        val mockProducts = listOf(
            Product(title = "Mechanical Keyboard", price = 2500.0, source = SearchType.MANUAL),
        )

        coEvery { repository.searchProductByManual(testQuery) } returns Result.success(mockProducts)

        val result = searchProductUseCase(testQuery, SearchType.MANUAL)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == mockProducts)

        coVerify { repository.searchProductByManual(testQuery) }

    }
}