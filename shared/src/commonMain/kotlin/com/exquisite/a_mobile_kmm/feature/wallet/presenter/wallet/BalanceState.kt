package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.BalanceModel

sealed class BalanceState {
    data object Idle : BalanceState()
    data object Loading : BalanceState()
    data class Success(val data: BalanceModel) : BalanceState()
    data class Error(val message: String) : BalanceState()
}
