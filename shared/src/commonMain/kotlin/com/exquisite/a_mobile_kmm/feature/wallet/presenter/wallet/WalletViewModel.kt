package com.exquisite.a_mobile_kmm.feature.wallet.presenter.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.CompleteTopUpAccountRequest
import com.exquisite.a_mobile_kmm.feature.wallet.domain.model.InitTopUpAccountRequest
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.CompleteTopUpAccountUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.GetCustomerBalanceUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.GetCustomerTransactionsUseCase
import com.exquisite.a_mobile_kmm.feature.wallet.domain.usecase.InitTopUpAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WalletViewModel(
    private val getCustomerBalanceUseCase: GetCustomerBalanceUseCase,
    private val getCustomerTransactionsUseCase: GetCustomerTransactionsUseCase,
    private val initTopUpAccountUseCase: InitTopUpAccountUseCase,
    private val completeTopUpAccountUseCase: CompleteTopUpAccountUseCase,
    private val dataStore: AMobileDataStore
) : ViewModel() {

    private var _walletState = MutableStateFlow<WalletState>(WalletState.Idle)
    val walletState = _walletState.asStateFlow()

    private val _ref = MutableStateFlow<String>("")
    val ref = _ref.asStateFlow()

    fun getCustomerBalance() {
        viewModelScope.launch {
            val  customerId  = dataStore.getUserId().first().toInt()
            _walletState.value = WalletState.Loading
            getCustomerBalanceUseCase.invoke(customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _walletState.value = WalletState.GetBalanceSuccess(response.data)
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }

    fun getCustomerTransactions() {
        viewModelScope.launch {
            val  customerId  = dataStore.getUserId().first().toInt()
            _walletState.value = WalletState.Loading
            getCustomerTransactionsUseCase.invoke(customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _walletState.value = WalletState.GetTransactionsSuccess(response.data)
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }

    fun initTopUpAccount(amount:Int) {
        viewModelScope.launch {
            val  customerId  = dataStore.getUserId().first().toInt()
            val request =  InitTopUpAccountRequest(customerId = customerId, amount = amount)
            _walletState.value = WalletState.Loading
            initTopUpAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {_walletState.value = WalletState.InitTopUpSuccess(response.data)}
                    is UseCaseResult.Error -> _walletState.value = WalletState.Error(response.message)
                }
            }
        }
    }

    fun completeTopUpAccount(txnRef:String) {
        viewModelScope.launch {
            val  customerId  = dataStore.getUserId().first().toInt()
            val  request = CompleteTopUpAccountRequest(
                customerId = customerId,
                ref = _ref.value,
                txnRef = txnRef
            )
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
