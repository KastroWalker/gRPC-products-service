package dev.kastro.resource

import dev.kastro.ProductServiceRequest
import dev.kastro.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
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
}