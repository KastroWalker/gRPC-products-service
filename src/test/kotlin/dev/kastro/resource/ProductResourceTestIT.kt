package dev.kastro.resource

import dev.kastro.FindByIdServiceRequest
import dev.kastro.ProductServiceRequest
import dev.kastro.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductResourceTestIT(
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {
    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        assertEquals(1, response.id)
        assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {
        val request = FindByIdServiceRequest.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with invalid id a ProductNotFoundException is returned`() {
        val request = FindByIdServiceRequest.newBuilder()
            .setId(10)
            .build()

        val description = "Product with ID ${request.id} not found"

        val response = Assertions.assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.findById(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }
}