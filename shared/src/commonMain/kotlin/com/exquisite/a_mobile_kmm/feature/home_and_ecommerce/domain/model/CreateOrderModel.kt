package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

data class CreateOrderModel(
    val orderRef: String,
    val requestToken: String,
    val shippingDetails: List<ShippingDetail>
)

data class ShippingDetail(
    val courierName: String,
    val courierId: String,
    val courierImage: String,
    val total: Double,
    val pickupEta: String,
    val pickupEtaTime: String,
    val deliveryEta: String,
    val deliveryEtaTime: String,
    val serviceCode: String
)
