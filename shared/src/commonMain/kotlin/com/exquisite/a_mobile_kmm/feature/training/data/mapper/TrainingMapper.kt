package com.exquisite.a_mobile_kmm.feature.training.data.mapper

import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.CompleteEnrollTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.EnrollTrainingByAccountBalanceResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.EnrollTrainingDataDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.GetActiveCoursesAndTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.InitEnrollTrainingResponseDto
import com.exquisite.a_mobile_kmm.feature.training.data.remote.response.TrainingCourseDto
import com.exquisite.a_mobile_kmm.feature.training.domain.model.EnrollmentSuccessModel
import com.exquisite.a_mobile_kmm.feature.training.domain.model.InitEnrollTrainingModel
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCourse
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCoursesModel

/**
 * Maps GetActiveCoursesAndTrainingResponseDto to TrainingCoursesModel
 */
fun GetActiveCoursesAndTrainingResponseDto.toTrainingCoursesModel(): TrainingCoursesModel? {
    val coursesList = data?.mapNotNull { it.toDomainModel() } ?: return null
    return if (coursesList.isNotEmpty()) {
        TrainingCoursesModel(courses = coursesList)
    } else {
        null
    }
}

fun TrainingCourseDto.toDomainModel(): TrainingCourse? {
    return if (id != null && title != null && description != null && bannerImageUrl != null &&
        author != null && authorImageUrl != null && type != null && isActive != null &&
        numberOfDays != null && created_at != null && updated_at != null) {
        TrainingCourse(
            id = id,
            title = title,
            description = description,
            bannerImageUrl = bannerImageUrl,
            author = author,
            authorImageUrl = authorImageUrl,
            type = type,
            isActive = isActive,
            resourceLink = resourceLink,
            numberOfDays = numberOfDays,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}

/**
 * Maps InitEnrollTrainingResponseDto to InitEnrollTrainingModel
 */
fun InitEnrollTrainingResponseDto.toInitEnrollTrainingModel(): InitEnrollTrainingModel? {
    val enrollData = data ?: return null
    return enrollData.toDomainModel()
}

fun EnrollTrainingDataDto.toDomainModel(): InitEnrollTrainingModel? {
    return if (ref != null && paymentLink != null) {
        InitEnrollTrainingModel(
            ref = ref,
            paymentLink = paymentLink
        )
    } else {
        null
    }
}

/**
 * Maps CompleteEnrollTrainingResponseDto to EnrollmentSuccessModel
 */
fun CompleteEnrollTrainingResponseDto.toEnrollmentSuccessModel(): EnrollmentSuccessModel {
    return EnrollmentSuccessModel(message = responseMessage)
}

/**
 * Maps EnrollTrainingByAccountBalanceResponseDto to EnrollmentSuccessModel
 */
fun EnrollTrainingByAccountBalanceResponseDto.toEnrollmentSuccessModel(): EnrollmentSuccessModel {
    return EnrollmentSuccessModel(message = responseMessage)
}
