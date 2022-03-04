package dev.kastro.util

import dev.kastro.ProductServiceRequest
import dev.kastro.exceptions.InvalidArgumentException

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank())
                    throw InvalidArgumentException("name")

                if (it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentException("price")

                return it
            }

            throw InvalidArgumentException("payload")
        }
    }
}