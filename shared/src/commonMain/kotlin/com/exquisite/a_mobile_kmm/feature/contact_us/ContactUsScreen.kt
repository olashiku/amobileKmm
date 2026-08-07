package com.exquisite.a_mobile_kmm.feature.contact_us

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold15
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold22
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14

data class ContactOption(
    val icon: String,
    val title: String,
    val subtitle: String,
    val backgroundColor: Color,
    val iconColor: Color,
    val action: ContactAction
)

enum class ContactAction {
    CALL, WHATSAPP, FACEBOOK, INSTAGRAM
}

@Composable
fun ContactUsScreen(
    onBackClick: (() -> Unit)? = null,
    onContactClick: ((ContactAction) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val directSupportOptions = listOf(
        ContactOption(
            icon = "🎧",
            title = "Customer Service",
            subtitle = "Voice call support",
            backgroundColor = Color(0xFFFFF7ED),
            iconColor = Color(0xFFF29100),
            action = ContactAction.CALL
        ),
        ContactOption(
            icon = "💬",
            title = "WhatsApp Chat",
            subtitle = "Instant messaging",
            backgroundColor = Color(0xFFECFDF5),
            iconColor = Color(0xFF10B981),
            action = ContactAction.WHATSAPP
        )
    )

    val socialMediaOptions = listOf(
        ContactOption(
            icon = "f",
            title = "Facebook",
            subtitle = "@cleaningservice_hq",
            backgroundColor = Color(0xFFEFF6FF),
            iconColor = Color(0xFF3B82F6),
            action = ContactAction.FACEBOOK
        ),
        ContactOption(
            icon = "📷",
            title = "Instagram",
            subtitle = "@cleaningservice_official",
            backgroundColor = Color(0xFFFDF2F8),
            iconColor = Color(0xFFDB2777),
            action = ContactAction.INSTAGRAM
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEEF2F6))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
                    .border(
                        width = 0.dp,
                        color = Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(0.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(0xFF1E293B),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(if (onBackClick != null) 50.dp else 0.dp))

                Text(
                    text = "Contact Us",
                    style = getPoppinsBold18(),
                    color = Color(0xFF1E293B)
                )
            }

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE2E8F0))
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                // Hero Text
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "How can we help?",
                        style = getPoppinsBold22(),
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Our team is here to support you. Choose your preferred way to reach us.",
                        style = getPoppinsMedium14(),
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Direct Support Section
                Text(
                    text = "DIRECT SUPPORT",
                    style = getPoppinsBold12(),
                    color = Color(0xFF64748B),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                directSupportOptions.forEach { option ->
                    ContactCard(
                        option = option,
                        onClick = { onContactClick?.invoke(option.action) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Social Media Section
                Text(
                    text = "SOCIAL MEDIA",
                    style = getPoppinsBold12(),
                    color = Color(0xFF64748B),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                socialMediaOptions.forEach { option ->
                    ContactCard(
                        option = option,
                        onClick = { onContactClick?.invoke(option.action) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Support Hours
                SupportHoursCard()

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ContactCard(
    option: ContactOption,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Icon Box
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(option.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = option.icon,
                fontSize = 20.sp,
                color = option.iconColor
            )
        }

        // Contact Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = option.title,
                style = getPoppinsBold15(),
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = option.subtitle,
                style = getPoppinsMedium12(),
                color = Color(0xFF64748B)
            )
        }

        // Arrow
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Open",
            tint = Color(0xFFCBD5E1),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SupportHoursCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F5F9))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Operating Hours",
            style = getPoppinsBold12(),
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Mon - Fri: 8:00 AM - 6:00 PM",
            style = getPoppinsMedium12(),
            color = Color(0xFF64748B),
            lineHeight = 18.sp
        )

        Text(
            text = "Sat - Sun: 9:00 AM - 4:00 PM",
            style = getPoppinsMedium12(),
            color = Color(0xFF64748B),
            lineHeight = 18.sp
        )
    }
}
