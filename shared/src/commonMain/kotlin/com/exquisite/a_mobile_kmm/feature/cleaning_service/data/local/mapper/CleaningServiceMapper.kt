package com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.mapper

import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.ApartmentTypeEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.CleaningTypeEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.NumberOfRoomsEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.data.local.entity.RegionEntity
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.ApartmentTypeModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningTypeModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.NumberOfRoomsModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.RegionModel

fun ApartmentTypeModel.toEntity(): ApartmentTypeEntity {
    return ApartmentTypeEntity(
        id = this.id,
        name = this.name
    )
}

fun ApartmentTypeEntity.toModel(): ApartmentTypeModel {
    return ApartmentTypeModel(
        id = this.id,
        name = this.name
    )
}

fun CleaningTypeModel.toEntity(): CleaningTypeEntity {
    return CleaningTypeEntity(
        id = this.id,
        name = this.name
    )
}

fun CleaningTypeEntity.toModel(): CleaningTypeModel {
    return CleaningTypeModel(
        id = this.id,
        name = this.name
    )
}

fun RegionModel.toEntity(): RegionEntity {
    return RegionEntity(
        id = this.id,
        name = this.name
    )
}

fun RegionEntity.toModel(): RegionModel {
    return RegionModel(
        id = this.id,
        name = this.name
    )
}

fun NumberOfRoomsModel.toEntity(): NumberOfRoomsEntity {
    return NumberOfRoomsEntity(
        id = this.id,
        name = this.name
    )
}

fun NumberOfRoomsEntity.toModel(): NumberOfRoomsModel {
    return NumberOfRoomsModel(
        id = this.id,
        name = this.name
    )
}
