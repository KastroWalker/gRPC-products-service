package dev.kastro.exceptions

import io.grpc.Status

class InvalidArgumentException(
    private val argumentName: String
) : BaseBusinessException() {
    override fun errorMessage(): String {
        return "Argument $argumentName is invalid."
    }

    override fun statusCode(): Status.Code {
        return Status.Code.INVALID_ARGUMENT
    }
}