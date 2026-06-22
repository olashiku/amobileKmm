package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.deliver_option

import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.InitPaymentModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.PaymentSuccessModel

sealed class DeliverOptionState {
    data object Idle : DeliverOptionState()
    data object Loading : DeliverOptionState()
    data class InitPaymentSuccess(val data: InitPaymentModel) : DeliverOptionState()
    data class PaymentSuccess(val data: PaymentSuccessModel) : DeliverOptionState()
    data class Error(val message: String) : DeliverOptionState()
}
