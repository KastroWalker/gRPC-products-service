package dev.kastro.exceptions

import io.grpc.Status

abstract class BaseBusinessException : java.lang.RuntimeException() {
    abstract fun errorMessage(): String
    abstract fun statusCode(): Status.Code
}