package dev.kastro.exceptions

import io.grpc.Status

class AlreadyExistsException(
    private val productName: String
) : BaseBusinessException() {
    override fun errorMessage(): String {
        return "Product $productName already exists"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.ALREADY_EXISTS
    }
}