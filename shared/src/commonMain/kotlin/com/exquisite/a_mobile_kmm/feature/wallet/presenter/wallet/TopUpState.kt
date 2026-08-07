package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.BalanceModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.TopUpDataModel

sealed class TopUpState {
    data object Idle : TopUpState()
    data object Loading : TopUpState()
    data class InitTopUpSuccess(val data: TopUpDataModel) : TopUpState()
    data class CompleteTopUpSuccess(val data: BalanceModel) : TopUpState()
    data class Error(val message: String) : TopUpState()
}
