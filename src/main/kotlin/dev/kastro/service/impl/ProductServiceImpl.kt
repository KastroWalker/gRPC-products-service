package dev.kastro.service.impl

import dev.kastro.dto.ProductReq
import dev.kastro.dto.ProductRes
import dev.kastro.service.ProductService
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl: ProductService {
    override fun create(req: ProductReq): ProductRes {
        TODO("Not yet implemented")
    }
}