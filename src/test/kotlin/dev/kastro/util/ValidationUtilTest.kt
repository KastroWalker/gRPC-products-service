package dev.kastro.util

import dev.kastro.ProductServiceRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValidationUtilTest {
    @Test
    fun `when validatePayload is call with valid data, should not throw exception`() {
        val request =
            ProductServiceRequest.newBuilder().setName("product name").setPrice(10.0).setQuantityInStock(10).build()

        Assertions.assertDoesNotThrow {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload is call with invalid product name, should throw exception`() {
        val request = ProductServiceRequest.newBuilder().setName("").setPrice(10.0).setQuantityInStock(10).build()

        Assertions.assertThrowsExactly(java.lang.IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload is call with invalid price, should throw exception`() {
        val request =
            ProductServiceRequest.newBuilder().setName("product name").setPrice(-10.0).setQuantityInStock(10).build()

        Assertions.assertThrowsExactly(java.lang.IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload is call with null payload, should throw exception`() {
        Assertions.assertThrowsExactly(java.lang.IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(null)
        }
    }
}