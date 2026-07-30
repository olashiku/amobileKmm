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
import kotlin.Int

/**
 * Maps GetActiveCoursesAndTrainingResponseDto to TrainingCoursesModel
 */
fun GetActiveCoursesAndTrainingResponseDto.toTrainingCoursesModel(): TrainingCoursesModel {
    val courses = data?.map{ it.toDomainModel() } ?:emptyList()
    return TrainingCoursesModel(courses = courses)
}

fun TrainingCourseDto.toDomainModel(): TrainingCourse {
    return TrainingCourse(
             id = id,
         title = title,
         description= description,
         bannerImageUrl = bannerImageUrl,
         author = author,
         authorImageUrl = authorImageUrl,
         type = type,
         isEnabled= isEnabled,
         resourceLink = resourceLink,
         numberOfDays= numberOfDays,
         amount = amount,
         tax = tax,
         trainingVenue = trainingVenue,
         startDate = startDate,
         endDate = endDate,
         createdAt = created_at,
         updatedAt = updated_at
        )
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
