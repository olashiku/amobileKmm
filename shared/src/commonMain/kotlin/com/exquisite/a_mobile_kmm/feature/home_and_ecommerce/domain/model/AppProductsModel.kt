package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

import kotlinx.serialization.Serializable

data class AppProductsModel(
    val categories: List<CategoryProduct>
)

data class CategoryProduct(
    val categoryId: Int,
    val category: String,
    val products: List<ProductItem>
)

@Serializable
data class ProductItem(
    val product: Product? = null,
    val images: List<String> = emptyList()
)
@Serializable
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

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val isEnabled: Boolean,
    val createdAt: String,
    val updatedAt: String
)

fun getCategoryProducts(): List<CategoryProduct> {
    val groundCleanersCategory = Category(
        id = 1,
        name = "Ground Cleaners",
        isEnabled = true,
        createdAt = "2025-06-26T00:00:00",
        updatedAt = "2025-07-20T19:24:19.219063"
    )

    val liquidCategory = Category(
        id = 8,
        name = "Liquid",
        isEnabled = true,
        createdAt = "2025-06-26T00:00:00",
        updatedAt = "2025-07-20T19:24:19.219063"
    )

    val sampleImageUrl = "https://res.cloudinary.com/dc6djvl7n/image/upload/properties_image/1750872176283_Screenshot%202025-06-21%20at%2003.40.03.png"
    val sampleImages = listOf(sampleImageUrl, sampleImageUrl, sampleImageUrl)

    return listOf(
        CategoryProduct(
            categoryId = 1,
            category = "Ground Cleaners",
            products = listOf(
                ProductItem(
                    product = Product(
                        id = 2,
                        sku = "SKU-66c21781-ba88-4203-b544-e5f7276a8497",
                        name = "Wood Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 5000.0,
                        quantity = 100,
                        status = true,
                        weight = 0.2,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-06-26",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 3,
                        sku = "SKU-9a8ba1d3-46c4-4848-acce-ea4c43e41d98",
                        name = "Degreaser",
                        description = "A degreaser",
                        price = 122.0,
                        quantity = 1221,
                        status = true,
                        weight = 0.5,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-07-19",
                        updateAt = "2025-07-19"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 4,
                        sku = "SKU-37baaf56-42b4-46e6-ad6c-9c00eda495f4",
                        name = "Detergent",
                        description = "A detergent",
                        price = 9000.0,
                        quantity = 122,
                        status = true,
                        weight = 0.15,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-07-19",
                        updateAt = "2025-07-19"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 6,
                        sku = "SKU-d9088231-e559-429c-8a6a-971197cc98e7",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden surfaces nright",
                        price = 11999.0,
                        quantity = 1,
                        status = true,
                        weight = 2.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 7,
                        sku = "SKU-0e15b0b4-cd6e-48fe-86f4-ca9ab3daab82",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 12000.0,
                        quantity = 1,
                        status = true,
                        weight = 2.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 8,
                        sku = "SKU-766ecbbc-6714-46e6-a572-a13cf4178c87",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 12000.0,
                        quantity = 1,
                        status = true,
                        weight = 9.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 9,
                        sku = "SKU-2b2d5d19-a57b-4a08-921d-b0d042779401",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 19000.0,
                        quantity = 1,
                        status = true,
                        weight = 9.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = groundCleanersCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                )
            )
        ),
        CategoryProduct(
            categoryId = 8,
            category = "Liquid",
            products = listOf(
                ProductItem(
                    product = Product(
                        id = 2,
                        sku = "SKU-66c21781-ba88-4203-b544-e5f7276a8497",
                        name = "Wood Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 5000.0,
                        quantity = 100,
                        status = true,
                        weight = 0.2,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-06-26",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 3,
                        sku = "SKU-9a8ba1d3-46c4-4848-acce-ea4c43e41d98",
                        name = "Degreaser",
                        description = "A degreaser",
                        price = 122.0,
                        quantity = 1221,
                        status = true,
                        weight = 0.5,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-07-19",
                        updateAt = "2025-07-19"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 4,
                        sku = "SKU-37baaf56-42b4-46e6-ad6c-9c00eda495f4",
                        name = "Detergent",
                        description = "A detergent",
                        price = 9000.0,
                        quantity = 122,
                        status = true,
                        weight = 0.15,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-07-19",
                        updateAt = "2025-07-19"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 6,
                        sku = "SKU-d9088231-e559-429c-8a6a-971197cc98e7",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden surfaces nright",
                        price = 11999.0,
                        quantity = 1,
                        status = true,
                        weight = 2.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 7,
                        sku = "SKU-0e15b0b4-cd6e-48fe-86f4-ca9ab3daab82",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 12000.0,
                        quantity = 1,
                        status = true,
                        weight = 2.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 8,
                        sku = "SKU-766ecbbc-6714-46e6-a572-a13cf4178c87",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 12000.0,
                        quantity = 1,
                        status = true,
                        weight = 9.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                ),
                ProductItem(
                    product = Product(
                        id = 9,
                        sku = "SKU-2b2d5d19-a57b-4a08-921d-b0d042779401",
                        name = "Wood Floor Cleaner",
                        description = "this cleaner cleans all wooden floow",
                        price = 19000.0,
                        quantity = 1,
                        status = true,
                        weight = 9.0,
                        isEnabled = false,
                        coverImageUrl = sampleImageUrl,
                        category = liquidCategory,
                        createdAt = "2025-07-20",
                        updateAt = "2025-07-20"
                    ),
                    images = sampleImages
                )
            )
        )
    )
}
