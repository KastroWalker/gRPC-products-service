package dev.kastro.service

import dev.kastro.dto.ProductReq
import dev.kastro.dto.ProductRes
import dev.kastro.dto.ProductUpdateReq

interface ProductService {
    fun create(req: ProductReq): ProductRes
    fun findById(id: Long): ProductRes
    fun update(req: ProductUpdateReq): ProductRes
    fun delete(id: Long)
    fun findAll(): List<ProductRes>
}