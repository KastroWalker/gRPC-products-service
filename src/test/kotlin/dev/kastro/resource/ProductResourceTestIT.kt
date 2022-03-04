package dev.kastro.resource

import dev.kastro.ProductServiceRequest
import dev.kastro.ProductServiceUpdateRequest
import dev.kastro.ProductsServiceGrpc.ProductsServiceBlockingStub
import dev.kastro.RequestById
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@MicronautTest
internal class ProductResourceTestIT(
    private val flyway: Flyway,
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {
    @BeforeEach
    fun setUp() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        assertEquals(3, response.id)
        assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {
        val request = RequestById.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with invalid id a ProductNotFoundException is returned`() {
        val request = RequestById.newBuilder()
            .setId(10)
            .build()

        val description = "Product with ID ${request.id} not found"

        val response = Assertions.assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.findById(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("Product A")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        val response = Assertions.assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.create(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals("Product ${request.name} already exists", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with valid data a success is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(2L)
            .setName("product name")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.update(request)

        assertEquals(2, response.id)
        assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsUpdateGrpc create method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(2L)
            .setName("product A")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        val response = Assertions.assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals("Product ${request.name} already exists", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid id a ProductNotFoundException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(10L)
            .setName("product W")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        val description = "Product with ID ${request.id} not found"

        val response = Assertions.assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with valid data a success is returned`() {
        val request = RequestById.newBuilder()
            .setId(2L)
            .build()

        assertDoesNotThrow {
            productsServiceBlockingStub.delete(request)
        }
    }

        @Test
    fun `when ProductsServiceGrpc delete method is call with invalid id a ProductNotFoundException is returned`() {
        val request = RequestById.newBuilder()
            .setId(10L)
            .build()

        val description = "Product with ID ${request.id} not found"

        val response = Assertions.assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.delete(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }
}