package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.request.CompleteTopUpAccountRequestDto
import com.exquisite.a_mobile_kmm.feature.wallet.data.remote.request.InitTopUpAccountRequestDto
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.CompleteTopUpAccountUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.GetCustomerBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.GetCustomerTransactionsUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.InitTopUpAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val getCustomerBalanceUseCase: GetCustomerBalanceUseCase,
    private val getCustomerTransactionsUseCase: GetCustomerTransactionsUseCase,
    private val initTopUpAccountUseCase: InitTopUpAccountUseCase,
    private val completeTopUpAccountUseCase: CompleteTopUpAccountUseCase
) : ViewModel() {

    private var _walletState = MutableStateFlow<WalletState>(WalletState.Idle)
    val walletState = _walletState.asStateFlow()

    fun getCustomerBalance(customerId: Int) {
        viewModelScope.launch {
            _walletState.value = WalletState.Loading
            getCustomerBalanceUseCase.invoke(customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _walletState.value = WalletState.GetBalanceSuccess(response.data)
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }

    fun getCustomerTransactions(customerId: Int) {
        viewModelScope.launch {
            _walletState.value = WalletState.Loading
            getCustomerTransactionsUseCase.invoke(customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _walletState.value = WalletState.GetTransactionsSuccess(response.data)
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }

    fun initTopUpAccount(request: InitTopUpAccountRequestDto) {
        viewModelScope.launch {
            _walletState.value = WalletState.Loading
            initTopUpAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _walletState.value = WalletState.InitTopUpSuccess(response.data)
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }

    fun completeTopUpAccount(request: CompleteTopUpAccountRequestDto) {
        viewModelScope.launch {
            _walletState.value = WalletState.Loading
            completeTopUpAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _walletState.value = WalletState.CompleteTopUpSuccess(response.data)
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }
}
