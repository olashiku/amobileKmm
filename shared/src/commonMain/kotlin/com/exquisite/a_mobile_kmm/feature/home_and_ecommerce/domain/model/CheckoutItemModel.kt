package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.RadioOption
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel

data class CheckoutItemModel(
    val title: String,
    val balance: Double
)

fun getCheckoutBalances(cartList : List<CartModel>, shippingDetails:ShippingDetail): List<CheckoutItemModel> {

    val totalAmount = cartList.sumOf { it.productPrice * it.quantity }
    val numberOfItems = cartList.sumOf {  it.quantity }
    val  taxAmount = totalAmount * 0.075

    return listOf(
        CheckoutItemModel(
            title = "Number of Items",
            balance = numberOfItems.toDouble()
        ),
        CheckoutItemModel(
            title = "Sub-Total",
            balance = totalAmount
        ),
        CheckoutItemModel(
            title = "Tax (7.5%)",
            balance = taxAmount
        ),
        CheckoutItemModel(
            title = "Delivery Fee",
            balance = shippingDetails.total
        )
    )
}

// Payment options
val paymentOptions = listOf(
    RadioOption(
        id = "wallet",
        title = "Pay with Wallet",
        subtitle = "Debit from your wallet"
    ),
    RadioOption(
        id = "standard",
        title = "Standard Payment",
        subtitle = "Other payment methods"

    )
)
