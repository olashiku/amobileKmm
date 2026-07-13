package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CheckoutItemModel


fun getBasicCleaningCheckoutBalances(basicCleaningBreakdownModel : BasicCleaningBreakdownModel): List<CheckoutItemModel> {

    val totalAmount = basicCleaningBreakdownModel.fee
    val  taxAmount = totalAmount * 0.075

    return listOf(
        CheckoutItemModel(
            title = "Sub-Total",
            balance = totalAmount
        ),
        CheckoutItemModel(
            title = "Tax (7.5%)",
            balance = taxAmount
        )
    )
}