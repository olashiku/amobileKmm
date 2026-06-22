package com.exquisite.a_mobile_kmm.feature.training.presenter.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.training.domain.usecase.GetActiveCoursesAndTrainingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainingViewModel(private val getActiveCoursesAndTrainingUseCase: GetActiveCoursesAndTrainingUseCase) : ViewModel() {

    private var _trainingState = MutableStateFlow<TrainingState>(TrainingState.Idle)
    val trainingState = _trainingState.asStateFlow()

    init {
        loadActiveCoursesAndTraining()
    }

    fun loadActiveCoursesAndTraining() {
        viewModelScope.launch {
            _trainingState.value = TrainingState.Loading
            getActiveCoursesAndTrainingUseCase.invoke().collect { response ->
                when (response) {
                    is UseCaseResult.Success ->
                        _trainingState.value = TrainingState.Success(response.data)
                    is UseCaseResult.Error ->
                        _trainingState.value = TrainingState.Error(response.message)
                }
            }
        }
    }

    fun clearState() {
        _trainingState.value = TrainingState.Idle
    }
}
