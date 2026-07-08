package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.presenter.deliver_option

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.cart.domain.model.CartModel
import com.exquisite.a_mobile_kmm.feature.cart.domain.usecase.CartUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.CompletePaymentRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DebitFromWalletRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.InitPaymentRequest
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ShippingDetail
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.CompletePaymentUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.DebitFromWalletUseCase
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase.InitPaymentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeliverOptionViewModel(
    private val initPaymentUseCase: InitPaymentUseCase,
    private val debitFromWalletUseCase: DebitFromWalletUseCase,
    private val completePaymentUseCase: CompletePaymentUseCase,
    val cartUseCase: CartUseCase,
    val dataStore : AMobileDataStore
    ) : ViewModel() {

    private var _deliverOptionState = MutableStateFlow<DeliverOptionState>(DeliverOptionState.Idle)
    val deliverOptionState = _deliverOptionState.asStateFlow()

    val _cartState = MutableStateFlow<List<CartModel>>(emptyList())
    val cartState =  _cartState.asStateFlow()


     init{
         getAllItems()
     }


    fun initPayment(request: InitPaymentRequest) {
        viewModelScope.launch {
            _deliverOptionState.value = DeliverOptionState.Loading
            initPaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _deliverOptionState.value = DeliverOptionState.InitPaymentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _deliverOptionState.value = DeliverOptionState.Error(response.message)
                }
            }
        }
    }

    fun debitFromWallet(request: DebitFromWalletRequest) {
        viewModelScope.launch {
            _deliverOptionState.value = DeliverOptionState.Loading
            debitFromWalletUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _deliverOptionState.value = DeliverOptionState.PaymentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _deliverOptionState.value = DeliverOptionState.Error(response.message)
                }
            }
        }
    }

    fun completePayment(orderRef:String,txnRef:String, ) {
        viewModelScope.launch {
            val customerId = dataStore.getUserId().first()
           val   request = CompletePaymentRequest(orderRef,txnRef,customerId.toInt())
            _deliverOptionState.value = DeliverOptionState.Loading
            completePaymentUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _deliverOptionState.value = DeliverOptionState.PaymentSuccess(response.data)
                    is UseCaseResult.Error ->
                        _deliverOptionState.value = DeliverOptionState.Error(response.message)
                }
            }
        }
    }

    fun getAllItems(){
        viewModelScope.launch {
            cartUseCase.getAllItems().collect{
                _cartState.value = it
            }
        }
    }

    fun clearState() {
        _deliverOptionState.value = DeliverOptionState.Idle
    }
     fun clearCart(){
         viewModelScope.launch(Dispatchers.IO) {
             cartUseCase.clearCart()
         }

     }
}
