package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderModel(
    val orderRef: String,
    val requestToken: String,
    val shippingDetails: List<ShippingDetail>
)

@Serializable
data class ShippingDetail(
    val courierName: String = "",
    val courierId: String = "",
    val courierImage: String = "",
    val total: Double = 0.0,
    val pickupEta: String = "",
    val pickupEtaTime: String = "",
    val deliveryEta: String = "",
    val deliveryEtaTime: String = "",
    val serviceCode: String = "",
    val isSelected: Boolean = false
)

fun CreateOrderModel.toInitPaymentRequest(courierId: String): InitPaymentRequest {
    val shippingDetail = shippingDetails.find { it.courierId == courierId }
    return InitPaymentRequest(
        orderRef = this.orderRef,
        requestToken = this.requestToken,
        courierId = shippingDetail?.courierId ?: "",
        serviceCode = shippingDetail?.serviceCode ?: "",
        shippingAmount = shippingDetail?.total?.toString() ?: "0.0"
    )
}

fun CreateOrderModel.toDebitFromWalletRequest(courierId: String): DebitFromWalletRequest {
    val shippingDetail = shippingDetails.find { it.courierId == courierId }
    return DebitFromWalletRequest(
        orderRef = this.orderRef,
        requestToken = this.requestToken,
        courierId = shippingDetail?.courierId ?: "",
        serviceCode = shippingDetail?.serviceCode ?: "",
        shippingAmount = shippingDetail?.total?.toString() ?: "0.0"
    )
}
