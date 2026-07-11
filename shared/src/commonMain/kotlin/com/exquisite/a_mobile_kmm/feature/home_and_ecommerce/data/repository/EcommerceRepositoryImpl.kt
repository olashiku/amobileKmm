package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.data_source.ProductDataSource
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.mapper.toCategoryEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.mapper.toProductEntity
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.local.mapper.toProductItemDtoList
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CompletePaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.CreateOrderRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.DebitFromWalletRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.request.InitPaymentRequestDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CategoryProductDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CompletePaymentResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.CreateOrderResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.DebitFromWalletResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.GetAppProductsResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.GetProductsByCategoryResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.remote.response.InitPaymentResponseDto
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.repository.EcommerceRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class EcommerceRepositoryImpl(
    private val httpClient: HttpClient,
    private val productDataSource: ProductDataSource
) : EcommerceRepository {

    override suspend fun getAppProducts(): Flow<Result<GetAppProductsResponseDto>> {
        return flow {
            val localCategories = productDataSource.getAllCategoryProducts().first()
            val localProducts = productDataSource.getAllProducts().first()

            if (localCategories.isNotEmpty() && localProducts.isNotEmpty()) {
                val categoryProductDtos = localCategories.map { categoryEntity ->
                    val productsForCategory = localProducts.filter { it.categoryId == categoryEntity.categoryId }
                    CategoryProductDto(
                        categoryId = categoryEntity.categoryId,
                        category = categoryEntity.category,
                        product = productsForCategory.toProductItemDtoList()
                    )
                }

                val response = GetAppProductsResponseDto(
                    responseMessage = "Success",
                    responseCode = "00",
                    data = categoryProductDtos
                )
                emit(Result.Success(response))
            }

            safeApiCall<GetAppProductsResponseDto> {
                httpClient.get("api/v1/product/get_app_products")
            }.collect { result ->
                if (result is Result.Success) {
                    result.data.data?.let { categoryProducts ->
                        val categoryEntities = categoryProducts.map { it.toCategoryEntity() }
                        productDataSource.saveCategoryProducts(categoryEntities)

                        val productEntities = categoryProducts.flatMap { categoryProduct ->
                            val categoryId = categoryProduct.categoryId ?: 0
                            val categoryName = categoryProduct.category ?: ""
                            val categoryIsEnabled = true
                            categoryProduct.product?.mapNotNull { productItem ->
                                productItem.toProductEntity(categoryId, categoryName, categoryIsEnabled)
                            } ?: emptyList()
                        }
                        productDataSource.saveProducts(productEntities)
                    }
                }
                emit(result)
            }
        }
    }

    override suspend fun getProductsByCategory(categoryId: Int): Flow<Result<GetProductsByCategoryResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/product/get_products_by_category_with_images") {
                parameter("categoryId", categoryId)
            }
        }
    }

    override suspend fun getAllProducts(
        pageNumber: Int,
        pageSize: Int,
        searchTerm: String?
    ): Flow<Result<GetProductsByCategoryResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/product/get_all_products") {
                parameter("pageNumber", pageNumber)
                parameter("pageSize", pageSize)
                if(searchTerm != null){
                    parameter("searchTerm", searchTerm)
                }
            }
        }
    }

    override suspend fun createOrder(request: CreateOrderRequestDto): Flow<Result<CreateOrderResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/order/create_order") {
                setBody(request)
            }
        }
    }

    override suspend fun initPayment(request: InitPaymentRequestDto): Flow<Result<InitPaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/order/init_payment") {
                setBody(request)
            }
        }
    }

    override suspend fun debitFromWallet(request: DebitFromWalletRequestDto): Flow<Result<DebitFromWalletResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/order/debit_from_wallet") {
                setBody(request)
            }
        }
    }

    override suspend fun completePayment(request: CompletePaymentRequestDto): Flow<Result<CompletePaymentResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/order/complete_payment") {
                setBody(request)
            }
        }
    }
}
