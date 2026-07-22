package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CheckoutItemModel


fun getPestControlResidentialCheckoutBalances(amount:String,residentialPestControlFormTwoModel:ResidentialPestControlFormTwoModel): List<CheckoutItemModel> {

    val totalAmount = amount.toDouble()
    val  taxAmount = totalAmount * 0.075
    val hotFoggingAmount = if(residentialPestControlFormTwoModel.wantsHotFogging) 30000.0 else 0.0
    val extraRoomAmount = if (residentialPestControlFormTwoModel.hasPestInVehicle ) 30000  * residentialPestControlFormTwoModel.numberOfVehicles.toDouble() else 0.0

    return listOf(
        CheckoutItemModel(
            title = "Hot Fogging",
            balance = hotFoggingAmount
        ),
        CheckoutItemModel(
            title = "Pest In Vehicle",
            balance = extraRoomAmount
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