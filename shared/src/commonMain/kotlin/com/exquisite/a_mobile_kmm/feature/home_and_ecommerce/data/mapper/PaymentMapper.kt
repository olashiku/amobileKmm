package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.mapper

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CompletePaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.DebitFromWalletRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.InitPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CompletePaymentRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DebitFromWalletRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.InitPaymentRequest

fun InitPaymentRequest.toDto(): InitPaymentRequestDto {
    return InitPaymentRequestDto(
        orderRef = this.orderRef,
        requestToken = this.requestToken,
        courierId = this.courierId,
        serviceCode = this.serviceCode,
        shippingAmount = this.shippingAmount
    )
}

fun DebitFromWalletRequest.toDto(): DebitFromWalletRequestDto {
    return DebitFromWalletRequestDto(
        orderRef = this.orderRef,
        requestToken = this.requestToken,
        courierId = this.courierId,
        serviceCode = this.serviceCode,
        shippingAmount = this.shippingAmount
    )
}

fun CompletePaymentRequest.toDto(): CompletePaymentRequestDto {
    return CompletePaymentRequestDto(
        orderRef = this.orderRef,
        txnRef = this.txnRef,
        customerId = this.customerId
    )
}
