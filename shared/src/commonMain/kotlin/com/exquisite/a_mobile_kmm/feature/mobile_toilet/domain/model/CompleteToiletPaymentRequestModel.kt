package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

data class CompleteToiletPaymentRequestModel(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
