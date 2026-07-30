package com.exquisite.a_mobile_kmm.feature.training.domain.model

import kotlinx.serialization.Serializable


data class TrainingCoursesModel(
    val courses: List<TrainingCourse> = emptyList()
)

@Serializable
data class TrainingCourse(
    val id: Int,
    val title: String,
    val description: String,
    val bannerImageUrl: String,
    val author: String,
    val authorImageUrl: String,
    val type: String,
    val isEnabled: Boolean,
    val resourceLink: String?,
    val numberOfDays: Int?,
    val amount: Double,
    val tax: Double?,
    val trainingVenue:String?,
    val startDate:String?,
    val endDate:String?,
    val createdAt: String,
    val updatedAt: String
)
