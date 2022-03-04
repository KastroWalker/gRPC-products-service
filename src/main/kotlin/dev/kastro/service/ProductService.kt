package dev.kastro.service

import dev.kastro.dto.ProductReq
import dev.kastro.dto.ProductRes

interface ProductService {
    fun create(req: ProductReq): ProductRes
    fun findById(id: Long): ProductRes
}