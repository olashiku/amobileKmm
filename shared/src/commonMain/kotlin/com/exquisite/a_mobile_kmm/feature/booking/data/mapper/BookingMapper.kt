package com.exquisite.a_mobile_kmm.feature.booking.data.mapper

import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.*
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.*

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
        bookingId != null && createdAt != null && updatedAt != null) {
        CustomerBooking(
            id = id,
            bookingType = bookingType,
            bookingDescription = bookingDescription,
            paymentStatus = paymentStatus,
            serviceStatus = serviceStatus,
            amountPaid = amountPaid,
            bookingId = bookingId,
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

fun CleaningBookingDataDto.toDomainModel(): CleaningBookingModel? {
    return if (id != null && region != null && location != null && apartmentType != null &&
        cleaningType != null && numberOfRooms != null && amount != null && address != null &&
        serviceType != null && createdAt != null && updatedAt != null) {

        val domainRegion = region.toDomainModel() ?: return null
        val domainLocation = location.toDomainModel() ?: return null
        val domainApartmentType = apartmentType.toDomainModel() ?: return null
        val domainCleaningType = cleaningType.toDomainModel() ?: return null
        val domainNumberOfRooms = numberOfRooms.toDomainModel() ?: return null

        CleaningBookingModel(
            id = id,
            region = domainRegion,
            location = domainLocation,
            apartmentType = domainApartmentType,
            cleaningType = domainCleaningType,
            numberOfRooms = domainNumberOfRooms,
            amount = amount,
            address = address,
            serviceType = serviceType,
            customerImages = customerImages ?: "",
            employeeImages = employeeImages ?: "",
            paymentStatus = paymentStatus ?: "",
            serviceStatus = serviceStatus ?: "",
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    } else {
        null
    }
}

fun RegionDto.toDomainModel(): Region? {
    return if (id != null && name != null) {
        Region(id = id, name = name)
    } else {
        null
    }
}

fun LocationDto.toDomainModel(): Location? {
    return if (id != null && name != null) {
        Location(id = id, name = name)
    } else {
        null
    }
}

fun ApartmentTypeDto.toDomainModel(): ApartmentType? {
    return if (name != null && id != null) {
        ApartmentType(name = name, id = id)
    } else {
        null
    }
}

fun CleaningTypeDto.toDomainModel(): CleaningType? {
    return if (id != null && name != null) {
        CleaningType(id = id, name = name)
    } else {
        null
    }
}

fun NumberOfRoomsDto.toDomainModel(): NumberOfRooms? {
    return if (id != null && name != null) {
        NumberOfRooms(id = id, name = name)
    } else {
        null
    }
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
        serviceStatus != null && createdAt != null && updatedAt != null) {
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
        paymentStatus != null && created_at != null && updated_at != null && serviceStatus != null) {

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
        uniqueRef != null && amount != null && created_at != null && updated_at != null) {

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
        created_at != null && updated_at != null) {
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
        createdAt != null && updatedAt != null) {

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
        uniqueRef != null) {
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
