package dev.kastro.util

import dev.kastro.ProductServiceRequest
import dev.kastro.ProductServiceUpdateRequest
import dev.kastro.exceptions.InvalidArgumentException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test

class ValidationUtilTest {
    @Test
    fun `when validatePayload is call with valid data, should not throw exception`() {
        val request =
            ProductServiceRequest.newBuilder()
                .setName("product name")
                .setPrice(10.0)
                .setQuantityInStock(10)
                .build()

        Assertions.assertDoesNotThrow {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload is call with valid data, should not throw exception`() {
        val request =
            ProductServiceUpdateRequest.newBuilder()
                .setId(1L)
                .setName("product name")
                .setPrice(10.0)
                .setQuantityInStock(10)
                .build()

        Assertions.assertDoesNotThrow {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload is call with invalid product name, should throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload is call with invalid product name, should throw exception`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("")
            .setPrice(10.0)
            .setQuantityInStock(10)
            .build()

        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload is call with invalid price, should throw exception`() {
        val request =
            ProductServiceRequest.newBuilder()
                .setName("product name")
                .setPrice(-10.0)
                .setQuantityInStock(10)
                .build()

        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload is call with invalid price, should throw exception`() {
        val request =
            ProductServiceUpdateRequest.newBuilder()
                .setId(1L)
                .setName("product name")
                .setPrice(-10.0)
                .setQuantityInStock(10)
                .build()

        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload is call with invalid id, should throw exception`() {
        val request =
            ProductServiceUpdateRequest.newBuilder()
                .setId(0)
                .setName("product name")
                .setPrice(10.0)
                .setQuantityInStock(10)
                .build()

        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload is call with null payload, should throw exception`() {
        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validatePayload(null)
        }
    }

    @Test
    fun `when validateUpdatePayload is call with null payload, should throw exception`() {
        assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(null)
        }
    }
}