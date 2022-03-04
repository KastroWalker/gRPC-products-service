package dev.kastro.service.impl

import dev.kastro.domain.Product
import dev.kastro.dto.ProductReq
import dev.kastro.exceptions.AlreadyExistsException
import dev.kastro.exceptions.ProductNotFoundException
import dev.kastro.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

internal class ProductServiceImplTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with data a ProductRes is returned`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.save(productInput))
            .thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.00, quantityInStock = 5)

        val productRes = productService.create(productReq)

        assertEquals(productReq.name, productRes.name)
    }

    @Test
    fun `when create method is call with duplicated product-name, throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.00, quantityInStock = 5)

        Assertions.assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.create((productReq))
        }
    }

    @Test
    fun `when findById method is call with valid id a ProductRes is returned`() {
        val productInput = 1L
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findById(productInput))
            .thenReturn(Optional.of(productOutput))

        val productRes = productService.findById(productInput)

        assertEquals(productInput, productRes.id)
        assertEquals(productOutput.name, productRes.name)
    }

    @Test
    fun `when findById method is call with invalid id, throws ProductNotFoundException`() {
        val id = 1L

        Assertions.assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.findById(id)
        }
    }
}