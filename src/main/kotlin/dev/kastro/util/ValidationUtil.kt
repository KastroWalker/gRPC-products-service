package dev.kastro.util

import dev.kastro.ProductServiceRequest
import dev.kastro.ProductServiceUpdateRequest
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

        fun validateUpdatePayload(payload: ProductServiceUpdateRequest?): ProductServiceUpdateRequest {
            payload?.let {
                if (it.id <= 0L)
                    throw InvalidArgumentException("ID")

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