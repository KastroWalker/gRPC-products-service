package dev.kastro.util

import dev.kastro.domain.Product
import dev.kastro.dto.ProductReq
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ProductConverterUtilTest {
    @Test
    fun `when toProductRes is call, should return a ProductRes with all data`() {
        val product = Product(
            id = 1,
            name = "product-name",
            price = 10.0,
            quantityInStock = 10
        )
        val productRes = product.toProductRes()

        Assertions.assertEquals(product.id, productRes.id)
        Assertions.assertEquals(product.name, productRes.name)
        Assertions.assertEquals(product.price, productRes.price)
        Assertions.assertEquals(product.quantityInStock, productRes.quantityInStock)
    }

        @Test
    fun `when toDomain is call, should return a Product with all data`() {
        val productReq = ProductReq(
            name = "product-name",
            price = 10.0,
            quantityInStock = 10
        )
        val product = productReq.toDomain()

        Assertions.assertEquals(null, product.id)
        Assertions.assertEquals(product.name, productReq.name)
        Assertions.assertEquals(product.price, productReq.price)
        Assertions.assertEquals(product.quantityInStock, productReq.quantityInStock)
    }
}