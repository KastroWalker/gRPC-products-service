package dev.kastro.repository

import dev.kastro.domain.Product
import io.micronaut.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long>