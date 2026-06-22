package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun WalletScreen(viewModel: WalletViewModel) {
    val state by viewModel.walletState.collectAsState()

    when (state) {
        is WalletState.Idle -> {
            // Initial state
        }
        is WalletState.Loading -> {
            // Show loading indicator
        }
        is WalletState.GetBalanceSuccess -> {
            // Show balance
        }
        is WalletState.GetTransactionsSuccess -> {
            // Show transactions list
        }
        is WalletState.InitTopUpSuccess -> {
            // Show payment link for top up
        }
        is WalletState.CompleteTopUpSuccess -> {
            // Show top up completion success
        }
        is WalletState.Error -> {
            // Show error message
        }
    }
}
