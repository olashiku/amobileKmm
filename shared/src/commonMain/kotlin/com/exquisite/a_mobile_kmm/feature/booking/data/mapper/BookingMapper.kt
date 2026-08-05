package com.exquisite.a_mobile_kmm.feature.booking.data.mapper

import com.exquisite.a_mobile_kmm.feature.booking.data.remote.request.RateAndReviewRequestDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.ApartmentTypeDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.AssignedAgentDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.CleaningBookingDataDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.CleaningTypeDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.CustomerBookingDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetCleaningBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetCustomerBookingsResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetPestControlBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetSepticBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetToiletBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.LocationDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.NumberOfRoomsDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.PestControlBookingDataDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.PreorderDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.RateAndReviewResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.RegionDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.SepticBookingDataDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.ServiceDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.ToiletBookingDataDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.ToiletEstimateDto
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ApartmentType
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.AssignedAgent
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CleaningBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CleaningType
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBooking
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CustomerBookingsModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.Location
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.NumberOfRooms
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.PestControlBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.Preorder
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.RateAndReviewRequest
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.RateReviewModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.Region
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.SepticBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.Service
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ToiletBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ToiletEstimate

/**
 * Maps GetCustomerBookingsResponseDto to CustomerBookingsModel
 */
fun GetCustomerBookingsResponseDto.toCustomerBookingsModel(): CustomerBookingsModel? {
    val bookingsList = data?.mapNotNull { it.toDomainModel() } ?: return null
    return if (bookingsList.isNotEmpty()) {
        CustomerBookingsModel(bookings = bookingsList)
    } else {
        null
    }
}

fun CustomerBookingDto.toDomainModel(): CustomerBooking? {
    return if (id != null && bookingType != null && bookingDescription != null &&
        bookingId != null && createdAt != null && updatedAt != null
    ) {
        CustomerBooking(
            id = id,
            bookingType = bookingType,
            bookingDescription = bookingDescription,
            paymentStatus = paymentStatus,
            serviceStatus = serviceStatus,
            amountPaid = amountPaid,
            bookingId = bookingId,
            assignedAgent = assignedAgent?.toDomainModel(),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

fun AssignedAgentDto.toDomainModel(): AssignedAgent? {
    return if (id != null && firstName != null && lastName != null &&
        email != null && phone != null && isActive != null &&
        createdAt != null && updatedAt != null
    ) {
        AssignedAgent(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            profilePictureUrl = profilePictureUrl,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps GetCleaningBookingResponseDto to CleaningBookingModel
 */
fun GetCleaningBookingResponseDto.toCleaningBookingModel(): CleaningBookingModel? {
    val bookingData = data ?: return null
    return bookingData.toDomainModel()
}

fun CleaningBookingDataDto.toDomainModel(): CleaningBookingModel {
    return CleaningBookingModel(
        id = id ?: 0,
        region = region?.toDomainModel() ?: Region(),
        location = location?.toDomainModel() ?: Location(),
        apartmentType = apartmentType?.toDomainModel() ?: ApartmentType(),
        cleaningType = cleaningType?.toDomainModel() ?: CleaningType(),
        numberOfRooms = numberOfRooms?.toDomainModel() ?: NumberOfRooms(),
        amount = amount ?: 0.0,
        address = address ?: "",
        serviceType = serviceType ?: "",
        cleaningDates = cleaningDates ?: "",
        cleaningTime = cleaningTime ?: "",
        customerImages = customerImages ?: emptyList(),
        employeeImages = employeeImages ?: emptyList(),
        paymentStatus = paymentStatus ?: "",
        serviceStatus = serviceStatus ?: "",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: ""
    )


}

fun RegionDto.toDomainModel(): Region {
    return Region(id = id ?: 0, name = name ?: "")
}

fun LocationDto.toDomainModel(): Location {
    return Location(id = id ?: 0, name = name ?: "")
}

fun ApartmentTypeDto.toDomainModel(): ApartmentType {
    return ApartmentType(name = name ?: "", id = id ?: 0)
}

fun CleaningTypeDto.toDomainModel(): CleaningType {
    return CleaningType(id = id ?: 0, name = name ?: "")
}

fun NumberOfRoomsDto.toDomainModel(): NumberOfRooms {
    return NumberOfRooms(id = id ?: 0, name = name ?: "")
}

/**
 * Maps GetSepticBookingResponseDto to SepticBookingModel
 */
fun GetSepticBookingResponseDto.toSepticBookingModel(): SepticBookingModel? {
    val bookingData = data ?: return null
    return bookingData.toDomainModel()
}

fun SepticBookingDataDto.toDomainModel(): SepticBookingModel? {
    return if (id != null && fullName != null && phoneNo != null && email != null &&
        address != null && dateOfExcavation != null && timeOfExcavation != null &&
        specialNote != null && liter != null && amount != null && paymentStatus != null &&
        serviceStatus != null && createdAt != null && updatedAt != null
    ) {
        SepticBookingModel(
            id = id,
            fullName = fullName,
            phoneNo = phoneNo,
            email = email,
            address = address,
            dateOfExcavation = dateOfExcavation,
            timeOfExcavation = timeOfExcavation,
            specialNote = specialNote,
            liter = liter,
            amount = amount,
            paymentStatus = paymentStatus,
            serviceStatus = serviceStatus,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

/**
 * Maps GetPestControlBookingResponseDto to PestControlBookingModel
 */
fun GetPestControlBookingResponseDto.toPestControlBookingModel(): PestControlBookingModel? {
    val bookingData = data ?: return null
    return bookingData.toDomainModel()
}

fun PestControlBookingDataDto.toDomainModel(): PestControlBookingModel? {
    return if (id != null && preorder != null && address != null && images != null &&
        propertyType != null && isHotFogging != null && serviceDate != null &&
        inspectionDate != null && serviceTime != null && inspectionTime != null &&
        customerOwnVehicle != null && numberOfVehicles != null && extraNote != null &&
        paymentStatus != null && created_at != null && updated_at != null && serviceStatus != null
    ) {

        val domainPreorder = preorder.toDomainModel() ?: return null

        PestControlBookingModel(
            id = id,
            preorder = domainPreorder,
            address = address,
            images = images,
            propertyType = propertyType,
            isHotFogging = isHotFogging,
            serviceDate = serviceDate,
            inspectionDate = inspectionDate,
            serviceTime = serviceTime,
            inspectionTime = inspectionTime,
            customerOwnVehicle = customerOwnVehicle,
            numberOfVehicles = numberOfVehicles,
            extraNote = extraNote,
            paymentStatus = paymentStatus,
            serviceStatus = serviceStatus,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}

fun PreorderDto.toDomainModel(): Preorder? {
    return if (id != null && numberOfRooms != null && service != null && customerId != null &&
        uniqueRef != null && amount != null && created_at != null && updated_at != null
    ) {

        val domainService = service.toDomainModel() ?: return null

        Preorder(
            id = id,
            numberOfRooms = numberOfRooms,
            service = domainService,
            customerId = customerId,
            uniqueRef = uniqueRef,
            amount = amount,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}

fun ServiceDto.toDomainModel(): Service? {
    return if (id != null && serviceName != null && basePrice != null &&
        created_at != null && updated_at != null
    ) {
        Service(
            id = id,
            serviceName = serviceName,
            basePrice = basePrice,
            extraRoomPrice = extraRoomPrice,
            createdAt = created_at,
            updatedAt = updated_at
        )
    } else {
        null
    }
}

/**
 * Maps GetToiletBookingResponseDto to ToiletBookingModel
 */
fun GetToiletBookingResponseDto.toToiletBookingModel(): ToiletBookingModel? {
    val bookingData = data ?: return null
    return bookingData.toDomainModel()
}

fun ToiletBookingDataDto.toDomainModel(): ToiletBookingModel? {
    return if (id != null && numberOfVipToilet != null && toiletEstimate != null &&
        companyName != null && companyEmail != null && recipientPhoneNumber != null &&
        numberOfStandardToilet != null && bookingDate != null && startDate != null &&
        startTime != null && endDate != null && endTime != null && isOverNight != null &&
        finishingDate != null && pictureOfEventLocation != null && pictureOfToiletPlacement != null &&
        typeOfEvent != null && extraNote != null && address != null && contactPersonEmail != null &&
        contactPersonName != null && paymentStatus != null && serviceStatus != null &&
        createdAt != null && updatedAt != null
    ) {

        val domainToiletEstimate = toiletEstimate.toDomainModel() ?: return null

        ToiletBookingModel(
            id = id,
            numberOfVipToilet = numberOfVipToilet,
            toiletEstimate = domainToiletEstimate,
            companyName = companyName,
            companyEmail = companyEmail,
            recipientPhoneNumber = recipientPhoneNumber,
            numberOfStandardToilet = numberOfStandardToilet,
            bookingDate = bookingDate,
            startDate = startDate,
            startTime = startTime,
            endDate = endDate,
            endTime = endTime,
            isOverNight = isOverNight,
            finishingDate = finishingDate,
            pictureOfEventLocation = pictureOfEventLocation,
            pictureOfToiletPlacement = pictureOfToiletPlacement,
            typeOfEvent = typeOfEvent,
            extraNote = extraNote,
            address = address,
            contactPersonEmail = contactPersonEmail,
            contactPersonName = contactPersonName,
            paymentStatus = paymentStatus,
            serviceStatus = serviceStatus,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

fun ToiletEstimateDto.toDomainModel(): ToiletEstimate? {
    return if (id != null && minimumNumberOfGuest != null && maximumNumberOfGuest != null &&
        serviceType != null && numberOfStandardToilet != null && numberOfVipToilets != null &&
        eventStartDate != null && eventEndDate != null && eventStartTIme != null &&
        eventEndTIme != null && numberOfDays != null && discountGiven != null &&
        overnight != null && totalNumberOfGuests != null && totalAmount != null &&
        recommendedNumberOfStandardToilets != null && recommendedNumberOfVipToilets != null &&
        uniqueRef != null
    ) {
        ToiletEstimate(
            id = id,
            minimumNumberOfGuest = minimumNumberOfGuest,
            maximumNumberOfGuest = maximumNumberOfGuest,
            serviceType = serviceType,
            numberOfStandardToilet = numberOfStandardToilet,
            numberOfVipToilets = numberOfVipToilets,
            eventStartDate = eventStartDate,
            eventEndDate = eventEndDate,
            eventStartTime = eventStartTIme,
            eventEndTime = eventEndTIme,
            numberOfDays = numberOfDays,
            discountGiven = discountGiven,
            overnight = overnight,
            totalNumberOfGuests = totalNumberOfGuests,
            totalAmount = totalAmount,
            recommendedNumberOfStandardToilets = recommendedNumberOfStandardToilets,
            recommendedNumberOfVipToilets = recommendedNumberOfVipToilets,
            uniqueRef = uniqueRef
        )
    } else {
        null
    }
}

/**
 * Maps RateAndReviewResponseDto to RateReviewModel
 */
fun RateAndReviewResponseDto.toRateReviewModel(): RateReviewModel {
    return RateReviewModel(message = responseMessage)
}

/**
 * Maps RateAndReviewRequest (domain model) to RateAndReviewRequestDto
 */
fun RateAndReviewRequest.toDto(): RateAndReviewRequestDto {
    return RateAndReviewRequestDto(
        serviceType = serviceType,
        comment = comment,
        rate = rate.toString(),
        customerId = customerId.toString(),
        bookingId = bookingId.toString()
    )
}
