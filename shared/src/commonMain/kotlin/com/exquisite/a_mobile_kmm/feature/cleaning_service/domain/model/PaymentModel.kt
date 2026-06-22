package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

data class PaymentModel(
    val authorizationUrl: String,
    val accessCode: String,
    val reference: String
)
