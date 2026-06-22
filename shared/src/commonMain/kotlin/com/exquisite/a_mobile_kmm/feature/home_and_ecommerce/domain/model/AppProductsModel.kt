package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

data class AppProductsModel(
    val categories: List<CategoryProduct>
)

data class CategoryProduct(
    val categoryId: Int,
    val category: String,
    val products: List<ProductItem>
)

data class ProductItem(
    val product: Product,
    val images: List<String>
)

data class Product(
    val id: Int,
    val sku: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val status: Boolean,
    val weight: Double,
    val isEnabled: Boolean,
    val coverImageUrl: String,
    val category: Category,
    val createdAt: String,
    val updateAt: String
)

data class Category(
    val id: Int,
    val name: String,
    val isEnabled: Boolean,
    val createdAt: String,
    val updatedAt: String
)
