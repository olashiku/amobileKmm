package com.exquisite.a_mobile_kmm.feature.training.domain.model

data class TrainingCoursesModel(
    val courses: List<TrainingCourse>
)

data class TrainingCourse(
    val id: Int,
    val title: String,
    val description: String,
    val bannerImageUrl: String,
    val author: String,
    val authorImageUrl: String,
    val type: String,
    val isActive: Boolean,
    val resourceLink: String?,
    val numberOfDays: Int,
    val createdAt: String,
    val updatedAt: String
)
