package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.RadioOption
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel

data class CheckoutItemModel(
    val title: String,
    val balance: Double
)

fun getCheckoutBalances(cartList : List<CartModel>): List<CheckoutItemModel> {
      val totalAmount = cartList.sumOf { it.productPrice * it.quantity }
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

// Payment options
val paymentOptions = listOf(
    RadioOption(
        id = "wallet",
        title = "Pay with Wallet",
        subtitle = "(Your Wallet Balance is $100)"
    ),
    RadioOption(
        id = "standard",
        title = "Standard Payment"
    )
)
