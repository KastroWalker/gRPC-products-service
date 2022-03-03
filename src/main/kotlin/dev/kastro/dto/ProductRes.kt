package dev.kastro.dto

data class ProductRes(
    val id: Long,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)