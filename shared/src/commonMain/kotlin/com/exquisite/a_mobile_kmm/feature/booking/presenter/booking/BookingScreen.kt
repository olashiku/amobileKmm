package com.exquisite.a_mobile_kmm.feature.booking.presenter.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookingScreen(
    viewModel: BookingViewModel = koinViewModel<BookingViewModel>(),
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 27.dp, vertical = 20.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "My Bookings", color = Color(0xff252525),
                    style = getPoppinsSemiBold18()
                )
            }
            Spacer(modifier = modifier.height(15.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)){
                repeat(10){
                    BookingItems()
                }
            }
        }
    }
}

@Composable
fun BookingItems(modifier: Modifier = Modifier) {

    Column{
        Text(text = "Cleaning", style = getPoppinsSemiBold18(), color = Color(0xFF252525))
        Spacer(modifier = modifier.height(15.dp))

        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xffFEF9F2)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                // ── "JOB COMPLETED" tag ──────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFDEFDA),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "JOB COMPLETED",
                        color = Color(0xFF252525),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── Job title ────────────────────────────────────────────────────
                Text(
                    text = "Move - In Cleaning (Deep)",
                    color = Color(0xFF252525),
                    style = getPoppinsSemiBold16(),

                )

                Spacer(modifier = Modifier.height(16.dp))

                // ── Date / time (blue + underline) ───────────────────────────────
                Text(
                    text = "Thurs, Oct 8, 2024 at 10:00 AM ",
                    color = Color(0xFF252525),
                   style = getPoppinsMedium12(),
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ── Divider ──────────────────────────────────────────────────────
                HorizontalDivider(
                    color = Color(0xFFE8E4DD),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ── Amount paid row ──────────────────────────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Amber circle with check icon
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .border(
                                width = 2.dp,
                                color =  Color(0xFFF09103),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Paid",
                            tint =  Color(0xFFF09103),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(
                        text = "Amount Paid 1000",
                        color =  Color(0xFF252525),
                        style = getPoppinsRegular14()
                    )
                }
            }
        }
    }
}



