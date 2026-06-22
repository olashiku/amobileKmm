package com.exquisite.a_mobile_kmm.feature.training.presenter.training

import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCoursesModel

sealed class TrainingState {
    data object Idle : TrainingState()
    data object Loading : TrainingState()
    data class Success(val data: TrainingCoursesModel) : TrainingState()
    data class Error(val message: String) : TrainingState()
}
