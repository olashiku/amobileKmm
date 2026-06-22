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
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val bannerImageUrl: String? = null,
    val author: String? = null,
    val authorImageUrl: String? = null,
    val type: String? = null,
    val isActive: Boolean? = null,
    val resourceLink: String? = null,
    val numberOfDays: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)
