package dev.kastro.service.impl

import dev.kastro.dto.ProductReq
import dev.kastro.dto.ProductRes
import dev.kastro.exceptions.AlreadyExistsException
import dev.kastro.exceptions.ProductNotFoundException
import dev.kastro.repository.ProductRepository
import dev.kastro.service.ProductService
import dev.kastro.util.toDomain
import dev.kastro.util.toProductRes
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(
    private val productRepository: ProductRepository
): ProductService {
    override fun create(req: ProductReq): ProductRes {
        verifyName(req.name)
        val productSaved = productRepository.save(req.toDomain())

        return productSaved.toProductRes()
    }

    override fun findById(id: Long): ProductRes {
        val findById = productRepository.findById(id)
        findById.orElseThrow {
            ProductNotFoundException(id)
        }
        return findById.get().toProductRes()
    }

    private fun verifyName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}