package com.exquisite.a_mobile_kmm.feature.training.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class GetActiveCoursesAndTrainingResponseDto(
    @Serializable(with = TrainingCourseListSerializer::class)
    val data: List<TrainingCourseDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object TrainingCourseListSerializer :
    EmptyObjectAsNullSerializer<List<TrainingCourseDto>>(ListSerializer(TrainingCourseDto.serializer()))

@Serializable
data class TrainingCourseDto(
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
    val created_at: String,
    val updated_at: String
)
