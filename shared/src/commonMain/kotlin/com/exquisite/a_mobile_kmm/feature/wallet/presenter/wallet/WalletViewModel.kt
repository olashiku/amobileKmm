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

    // Separate states for balance and transactions to avoid conflicts
    private var _balanceState = MutableStateFlow<BalanceState>(BalanceState.Idle)
    val balanceState = _balanceState.asStateFlow()

    private var _transactionsState = MutableStateFlow<TransactionsState>(TransactionsState.Idle)
    val transactionsState = _transactionsState.asStateFlow()

    private var _topUptState = MutableStateFlow<TopUpState>(TopUpState.Idle)
    val topUptState = _topUptState.asStateFlow()

    private val _ref = MutableStateFlow<String>("")
    val ref = _ref.asStateFlow()

    fun getCustomerBalance() {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()
            _balanceState.value = BalanceState.Loading
            getCustomerBalanceUseCase.invoke(customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _balanceState.value =
                        BalanceState.Success(response.data)

                    is UseCaseResult.Error -> _balanceState.value =
                        BalanceState.Error(response.message)
                }
            }
        }
    }

    fun getCustomerTransactions() {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()
            _transactionsState.value = TransactionsState.Loading
            getCustomerTransactionsUseCase.invoke(customerId).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _transactionsState.value =
                        TransactionsState.Success(response.data)

                    is UseCaseResult.Error -> _transactionsState.value =
                        TransactionsState.Error(response.message)
                }
            }
        }
    }

    fun initTopUpAccount(amount: Int) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()
            val request = InitTopUpAccountRequest(customerId = customerId, amount = amount)
            _topUptState.value = TopUpState.Loading
            initTopUpAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> {
                        _ref.value = response.data.ref
                        _topUptState.value = TopUpState.InitTopUpSuccess(response.data)
                    }

                    is UseCaseResult.Error -> _topUptState.value =
                        TopUpState.Error(response.message)
                }
            }
        }
    }

    fun completeTopUpAccount(txnRef: String) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first().toInt()
            val request = CompleteTopUpAccountRequest(
                customerId = customerId,
                ref = ref.value,
                txnRef = txnRef
            )
            _topUptState.value = TopUpState.Loading
            completeTopUpAccountUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _topUptState.value =
                        TopUpState.CompleteTopUpSuccess(response.data)

                    is UseCaseResult.Error -> _topUptState.value =
                        TopUpState.Error(response.message)
                }
            }
        }
    }

    fun clearTopUpState(){
        _topUptState.value = TopUpState.Idle
    }
}
