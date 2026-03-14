package com.example.dreamscanfix.domain.use_case

import com.example.dreamscanfix.domain.model.Product
import com.example.dreamscanfix.domain.repository.ProductRepository
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import kotlinx.coroutines.test.runTest



class SaveProductUseCaseTest {

    private val repository: ProductRepository = mockk(relaxed = true)
    private val saveProductUseCase = SaveProductUseCase(repository)


    @Test
    fun `should trigger savedProduct when called` () = runTest {

        val product = Product(title = "Mechanical Keyboard", price = 2500.0)

        saveProductUseCase(product)

        coVerify { repository.saveProduct(product) }

    }
}