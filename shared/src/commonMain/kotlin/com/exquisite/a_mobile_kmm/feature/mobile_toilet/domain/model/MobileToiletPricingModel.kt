package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.SummaryData

fun getToiletPricingList(toiletPriceModel: ToiletPriceModel): List<SummaryData> {
    return listOf(
        SummaryData("Number Of Days Booked", toiletPriceModel.numberOfDays.toString()),
        SummaryData("Total Number Of Guests", toiletPriceModel.totalNumberOfGuests.toString()),
        SummaryData("Recommended Number Of Standard Toilets", toiletPriceModel.recommendedNumberOfStandardToilets.toString()),
        SummaryData("Recommended Number Of Vip Toilets", toiletPriceModel.recommendedNumberOfVipToilets.toString()),
        SummaryData("OverNight Charge", toiletPriceModel.overnight.formatBalance()),
        SummaryData("Discount Given", toiletPriceModel.discountGiven.formatBalance()),
    )
}