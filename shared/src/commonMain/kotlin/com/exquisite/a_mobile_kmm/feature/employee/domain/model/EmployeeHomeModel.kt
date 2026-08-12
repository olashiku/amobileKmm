package com.exquisite.a_mobile_kmm.feature.employee.domain.model

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.cleaning_icon
import amobilekmm.shared.generated.resources.mobile_toilet
import amobilekmm.shared.generated.resources.pest_control
import amobilekmm.shared.generated.resources.septic
import org.jetbrains.compose.resources.DrawableResource

data class EmployeeHomeModel(
    val icon:DrawableResource,
    val name:String,
    val tag:String
)

fun getEmployeeMenuItem():List<EmployeeHomeModel>{
    return listOf(
        EmployeeHomeModel(Res.drawable.cleaning_icon,"Cleaning", "cleaning"),
        EmployeeHomeModel(Res.drawable.mobile_toilet,"Mobile\nToilet","mobile_toilet"),
        EmployeeHomeModel(Res.drawable.pest_control,"Pests\nControl","pest_control"),
        EmployeeHomeModel( Res.drawable.septic,"Septic\nRequest","septic_request"),
    )
}