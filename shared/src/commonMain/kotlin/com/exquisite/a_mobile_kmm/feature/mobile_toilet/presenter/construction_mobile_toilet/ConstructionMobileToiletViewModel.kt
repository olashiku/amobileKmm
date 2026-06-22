package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.construction_mobile_toilet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request.RequestForConstructionRequestDto
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.usecase.RequestForConstructionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConstructionMobileToiletViewModel(
    private val requestForConstructionUseCase: RequestForConstructionUseCase
) : ViewModel() {

    private var _constructionMobileToiletState = MutableStateFlow<ConstructionMobileToiletState>(ConstructionMobileToiletState.Idle)
    val constructionMobileToiletState = _constructionMobileToiletState.asStateFlow()

    fun requestForConstruction(request: RequestForConstructionRequestDto) {
        viewModelScope.launch {
            _constructionMobileToiletState.value = ConstructionMobileToiletState.Loading
            requestForConstructionUseCase.invoke(request).collect { response ->
                when (response) {
                    is UseCaseResult.Success -> _constructionMobileToiletState.value = ConstructionMobileToiletState.Success(response.data)
                    is UseCaseResult.Error -> _constructionMobileToiletState.value = ConstructionMobileToiletState.Error(response.message)
                }
            }
        }
    }
}
