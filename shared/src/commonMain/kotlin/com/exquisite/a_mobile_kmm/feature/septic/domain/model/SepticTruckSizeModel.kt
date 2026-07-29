package com.exquisite.a_mobile_kmm.feature.septic.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SepticTruckSizeModel(
    val id: Int,
    val liter: Int,
    val price: Double
)
