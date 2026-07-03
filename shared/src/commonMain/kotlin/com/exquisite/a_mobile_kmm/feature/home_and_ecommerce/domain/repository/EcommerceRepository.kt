package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CompletePaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CreateOrderRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.DebitFromWalletRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.InitPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CompletePaymentResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CreateOrderResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.DebitFromWalletResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.GetAppProductsResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.GetProductsByCategoryResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.InitPaymentResponseDto
import kotlinx.coroutines.flow.Flow

interface EcommerceRepository {
    suspend fun getAppProducts(): Flow<Result<GetAppProductsResponseDto>>
    suspend fun getProductsByCategory(categoryId: Int): Flow<Result<GetProductsByCategoryResponseDto>>
    suspend fun getAllProducts(pageNumber: Int, pageSize: Int, searchTerm: String?): Flow<Result<GetProductsByCategoryResponseDto>>
    suspend fun createOrder(request: CreateOrderRequestDto): Flow<Result<CreateOrderResponseDto>>
    suspend fun initPayment(request: InitPaymentRequestDto): Flow<Result<InitPaymentResponseDto>>
    suspend fun debitFromWallet(request: DebitFromWalletRequestDto): Flow<Result<DebitFromWalletResponseDto>>
    suspend fun completePayment(request: CompletePaymentRequestDto): Flow<Result<CompletePaymentResponseDto>>
}
