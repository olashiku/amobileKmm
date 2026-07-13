package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CheckoutItemModel


fun getDeepCleaningCheckoutBalances(cleaningPriceModel : CleaningPriceModel,
    deepCleaningFormModel:DeepCleaningFormModel): List<CheckoutItemModel> {

    val totalAmount = cleaningPriceModel.amount
    val postConstructionFee = if (deepCleaningFormModel.postConstruction)  totalAmount * 0.15 else 0.0
    val  taxAmount = totalAmount * 0.075

    return listOf(
        CheckoutItemModel(
            title = "Post-Construction Fee",
            balance = postConstructionFee
        ),
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