package dev.kastro.util

import dev.kastro.ProductServiceRequest

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank())
                    throw IllegalArgumentException("Name cannot be null or empty")

                if (it.price.isNaN() || it.price < 0)
                    throw IllegalArgumentException("Price should be a number")

                return it
            }

            throw IllegalArgumentException()
        }
    }
}