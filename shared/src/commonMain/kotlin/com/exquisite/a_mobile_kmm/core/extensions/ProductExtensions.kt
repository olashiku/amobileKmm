package com.exquisite.a_mobile_kmm.core.extensions

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.Product
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem

/**
 * Converts a Product to a ProductItem
 * @param images List of image URLs for the product. Defaults to a list containing only the coverImageUrl
 * @return ProductItem with the product and its images
 */
fun Product.toProductItem(images: List<String>? = null): ProductItem {
    return ProductItem(
        product = this,
        images = images ?: listOf(this.coverImageUrl)
    )
}

/**
 * Converts a list of Products to a list of ProductItems
 * @param imageMapping Optional map of product ID to list of image URLs
 * @return List of ProductItems
 */
fun List<Product>.toProductItems(imageMapping: Map<Int, List<String>>? = null): List<ProductItem> {
    return this.map { product ->
        product.toProductItem(imageMapping?.get(product.id))
    }
}
