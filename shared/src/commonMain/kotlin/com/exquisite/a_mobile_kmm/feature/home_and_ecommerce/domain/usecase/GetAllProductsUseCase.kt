package com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.data.mapper.toProductsListModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductItem
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.ProductsListModel
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.repository.EcommerceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetAllProductsUseCase(private val ecommerceRepository: EcommerceRepository) {

    suspend operator fun invoke(
        pageNumber: Int,
        pageSize: Int,
        searchTerm: String?
    ): Flow<UseCaseResult< List<ProductItem>>> {
        return ecommerceRepository.getAllProducts(pageNumber, pageSize, searchTerm).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val productsListModel = result.data.toProductsListModel()
                        UseCaseResult.Success(productsListModel)
                    } else {
                        UseCaseResult.Error(result.data.responseMessage)
                    }
                }
                is Result.Exception -> {
                    UseCaseResult.Error(result.exception.handleException())
                }
            }
        }.catch { exception ->
            emit(UseCaseResult.Error(exception.handleException()))
        }.flowOn(Dispatchers.IO)
    }
}
