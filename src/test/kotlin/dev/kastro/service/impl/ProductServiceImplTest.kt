package dev.kastro.service.impl

import dev.kastro.domain.Product
import dev.kastro.dto.ProductReq
import dev.kastro.dto.ProductRes
import dev.kastro.dto.ProductUpdateReq
import dev.kastro.exceptions.AlreadyExistsException
import dev.kastro.exceptions.ProductNotFoundException
import dev.kastro.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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

        assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.create((productReq))
        }
    }

    @Test
    fun `when update method is call with duplicated product-name, throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productReq = ProductUpdateReq(id = 1L, name = "product name", price = 10.00, quantityInStock = 5)

        assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.update((productReq))
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

        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.findById(id)
        }
    }

    @Test
    fun `when update method is call with data a ProductRes is returned`() {
        val productInput = Product(id = 1, name = "updated product", price = 11.00, quantityInStock = 6)
        val findByIdOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findById(productInput.id!!))
            .thenReturn(Optional.of(findByIdOutput))

        `when`(productRepository.update(productInput))
            .thenReturn(productInput)

        val productReq = ProductUpdateReq(id = 1, name = "updated product", price = 11.00, quantityInStock = 6)

        val productRes = productService.update(productReq)

        assertEquals(productReq.name, productRes.name)
    }

    @Test
    fun `when update method is call with invalid id, throws ProductNotFoundException`() {
        val productReq = ProductUpdateReq(id = 1L, name = "product name", price = 10.00, quantityInStock = 5)

        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.update(productReq)
        }
    }

    @Test
    fun `when delete method is call with valid id the product is deleted`() {
        val id = 1L
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findById(id))
            .thenReturn(Optional.of(productOutput))

        assertDoesNotThrow {
            productService.delete(id)
        }
    }

    @Test
    fun `when delete method is call with invalid id, throws ProductNotFoundException`() {
        val id = 1L

        `when`(productRepository.findById(id))
            .thenReturn(Optional.empty())

        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.delete(id)
        }
    }

    @Test
    fun `when findAll method is call a list of ProductRes is returned`() {
        val productList = listOf(Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5))

        `when`(productRepository.findAll())
            .thenReturn(productList)

        val productRes = productService.findAll()

        assertEquals(productList[0].name, productRes[0].name)
    }

    @Test
    fun `when findAll method is call without products an emptyList of ProductRes is returned`() {
        val productList = emptyList<ProductRes>()

        `when`(productRepository.findAll())
            .thenReturn(emptyList())

        val productRes = productService.findAll()

        assertEquals(productList.size, productRes.size)
    }
}