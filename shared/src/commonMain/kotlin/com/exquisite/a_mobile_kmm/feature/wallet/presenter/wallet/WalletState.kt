package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.BalanceModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.TopUpDataModel
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.TransactionModel

sealed class WalletState {
    data object Idle : WalletState()
    data object Loading : WalletState()
    data class GetBalanceSuccess(val data: BalanceModel) : WalletState()
    data class GetTransactionsSuccess(val data: List<TransactionModel>) : WalletState()
    data class InitTopUpSuccess(val data: TopUpDataModel) : WalletState()
    data class CompleteTopUpSuccess(val data: BalanceModel) : WalletState()
    data class Error(val message: String) : WalletState()
}
