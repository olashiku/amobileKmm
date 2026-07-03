package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.mapper

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CategoryDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CategoryProductDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CompletePaymentResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CreateOrderDataDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CreateOrderResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.DebitFromWalletResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.GetAppProductsResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.GetProductsByCategoryResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.InitPaymentResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.ProductDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.ProductItemDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.ShippingDetailDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.AppProductsModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.Category
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CategoryProduct
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CreateOrderModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.InitPaymentModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.PaymentSuccessModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.Product
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ShippingDetail

/**
 * Maps GetAppProductsResponseDto to AppProductsModel domain model
 * Returns null if any required fields are missing
 */
fun GetAppProductsResponseDto.toAppProductsModel(): AppProductsModel {
    val categoriesList = data?.map { it.toDomainModel() }
    return AppProductsModel(categories = categoriesList?:emptyList())

}

/**
 * Maps CategoryProductDto to CategoryProduct domain model
 * Returns null if any required fields are missing
 */
fun CategoryProductDto.toDomainModel(): CategoryProduct {
        val productsList = product?.mapNotNull { it.toDomainModel() }
   return CategoryProduct(
        categoryId = categoryId?:0,
        category = category?:"",
        products = productsList?:emptyList()
    )

}

/**
 * Maps ProductItemDto to ProductItem domain model
 * Returns null if any required fields are missing
 */
fun ProductItemDto.toDomainModel(): ProductItem? {
    val domainProduct = product?.toDomainModel() ?: return null
    val imagesList = image ?: emptyList()
    return ProductItem(
        product = domainProduct,
        images = imagesList
    )
}

/**
 * Maps ProductDto to Product domain model
 * Returns null if any required fields are missing
 */
fun ProductDto.toDomainModel(): Product? {
    return if (id != null &&
        sku != null &&
        name != null &&
        description != null &&
        price != null &&
        quantity != null &&
        status != null &&
        weight != null &&
        isEnabled != null &&
        coverImageUrl != null &&
        category != null &&
        createdAt != null &&
        updateAt != null) {
        val domainCategory = category.toDomainModel() ?: return null
        Product(
            id = id,
            sku = sku,
            name = name,
            description = description,
            price = price,
            quantity = quantity,
            status = status,
            weight = weight,
            isEnabled = isEnabled,
            coverImageUrl = coverImageUrl,
            category = domainCategory,
            createdAt = createdAt,
            updateAt = updateAt
        )
    } else {
        null
    }
}

/**
 * Maps CategoryDto to Category domain model
 * Returns null if any required fields are missing
 */
fun CategoryDto.toDomainModel(): Category? {
    return if (id != null && name != null && isEnabled != null && createdAt != null && updatedAt != null) {
        Category(
            id = id,
            name = name,
            isEnabled = isEnabled,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps GetProductsByCategoryResponseDto to ProductsListModel domain model
 * Returns an empty ProductsListModel if data is null or empty
 */
fun GetProductsByCategoryResponseDto.toProductsListModel(): List<ProductItem> {
    val productsList = data?.mapNotNull { it.toDomainModel() } ?: emptyList()
    return productsList
}



/**
 * Maps CreateOrderResponseDto to CreateOrderModel domain model
 * Returns null if any required fields are missing
 */
fun CreateOrderResponseDto.toCreateOrderModel(): CreateOrderModel? {
    val orderData = data ?: return null
    return orderData.toDomainModel()
}

/**
 * Maps CreateOrderDataDto to CreateOrderModel domain model
 * Returns null if any required fields are missing
 */
fun CreateOrderDataDto.toDomainModel(): CreateOrderModel? {
    return if (order_ref != null && request_token != null && shippingDetails != null) {
        val shippingList = shippingDetails.mapNotNull { it.toDomainModel() }
        if (shippingList.isNotEmpty()) {
            CreateOrderModel(
                orderRef = order_ref,
                requestToken = request_token,
                shippingDetails = shippingList
            )
        } else {
            null
        }
    } else {
        null
    }
}

/**
 * Maps ShippingDetailDto to ShippingDetail domain model
 * Returns null if any required fields are missing
 */
fun ShippingDetailDto.toDomainModel(): ShippingDetail? {
    return if (courier_name != null &&
        courier_id != null &&
        courier_image != null &&
        total != null &&
        pickup_eta != null &&
        pickup_eta_time != null &&
        delivery_eta != null &&
        delivery_eta_time != null &&
        service_code != null) {
        ShippingDetail(
            courierName = courier_name,
            courierId = courier_id,
            courierImage = courier_image,
            total = total,
            pickupEta = pickup_eta,
            pickupEtaTime = pickup_eta_time,
            deliveryEta = delivery_eta,
            deliveryEtaTime = delivery_eta_time,
            serviceCode = service_code
        )
    } else {
        null
    }
}

/**
 * Maps InitPaymentResponseDto to InitPaymentModel domain model
 * Returns null if any required fields are missing
 */
fun InitPaymentResponseDto.toInitPaymentModel(): InitPaymentModel? {
    return if (data != null) {
        InitPaymentModel(paymentUrl = data)
    } else {
        null
    }
}

/**
 * Maps DebitFromWalletResponseDto to PaymentSuccessModel domain model
 */
fun DebitFromWalletResponseDto.toPaymentSuccessModel(): PaymentSuccessModel {
    return PaymentSuccessModel(message = responseMessage)
}

/**
 * Maps CompletePaymentResponseDto to PaymentSuccessModel domain model
 */
fun CompletePaymentResponseDto.toPaymentSuccessModel(): PaymentSuccessModel {
    return PaymentSuccessModel(message = responseMessage)
}
