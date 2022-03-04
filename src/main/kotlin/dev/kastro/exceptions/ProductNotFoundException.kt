package dev.kastro.exceptions

import io.grpc.Status

class ProductNotFoundException(
    private val productId: Long
) : BaseBusinessException() {
    override fun errorMessage(): String {
        return "Product with ID $productId not found"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.NOT_FOUND
    }
}