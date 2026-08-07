package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.TransactionModel

sealed class TransactionsState {
    data object Idle : TransactionsState()
    data object Loading : TransactionsState()
    data class Success(val data: List<TransactionModel>) : TransactionsState()
    data class Error(val message: String) : TransactionsState()
}
