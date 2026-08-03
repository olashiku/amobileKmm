package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.RadioOption


val mobileToiletOption = listOf(
    RadioOption(
        id = "event",
        title = "Mobile toilet for all events",
        subtitle = "For weddings, festivals, parties, and outdoor gatherings"
    ),
    RadioOption(
        id = "construction",
        title = "Mobile toilet for construction",
        subtitle = "For construction sites, job sites, and work crews"
    )
)
val mobileToiletTypeOption = listOf(
    RadioOption(
        id = "standard",
        title = "Standard Toilets",
        subtitle = "For weddings, festivals, parties, and outdoor gatherings"
    ),
    RadioOption(
        id = "vip",
        title = "VIP Toilets",
        subtitle = "Upscale restrooms for weddings, corporate events, and upscale gatherings"
    ),
    RadioOption(
        id = "both",
        title = "Both Toilets",
        subtitle = "A mix of standard and VIP units for events with varied needs"
    )
)
