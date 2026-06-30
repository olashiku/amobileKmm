package com.exquisite.a_mobile_kmm.feature.training.presenter.training

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screen_components.SearchTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold24
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.booking.presenter.booking.BookingItems

@Composable
fun TrainingScreen(modifier: Modifier = Modifier
) {

    var searchQuery by remember {mutableStateOf("")}
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
            Spacer(modifier = modifier.height(26.dp))
            SearchTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Search...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = modifier.height(18.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Available Training Courses", color = Color(0xff252525),
                    style = getPoppinsBold24()
                )
            }
            Spacer(modifier = modifier.height(15.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)){
                repeat(10){
                    CourseCard(
                        imageUrl = "https://res.cloudinary.com/dc6djvl7n/image/upload/properties_image/1750872176283_Screenshot%202025-06-21%20at%2003.40.03.png",
                        title = "Hello world",
                        rating = "4.5",
                        views = "315",
                        duration = "3 Days",
                        instructorImageUrl = "https://res.cloudinary.com/dc6djvl7n/image/upload/properties_image/1750872176283_Screenshot%202025-06-21%20at%2003.40.03.png",
                        instructorName = "Oluwayemisi oguntayo",
                        price = "$15"
                    )
                }
            }
        }
    }
}

private val OrangeAccent  = Color(0xFFFF8C00)
private val CardBg        = Color(0xFFFDF8EE)   // warm cream background
private val TextPrimary   = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF888888)

@Composable
fun CourseCard(
    imageUrl: String,
    title: String,
    rating: String,
    views: String,
    duration: String,
    instructorImageUrl: String,
    instructorName: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column {

            // ── Cover image ───────────────────────────────────────────────────
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )

            Column(modifier = Modifier.padding(14.dp)) {

                // ── Title ─────────────────────────────────────────────────────
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(Modifier.height(6.dp))

                // ── Rating + views + duration ─────────────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = rating,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                    Text(
                        text = "  |  $views views",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    Spacer(Modifier.weight(1f))

                    // Clock icon + duration
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(OrangeAccent)
                    ) {
                        Text("⏱", fontSize = 11.sp)
                    }
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = duration,
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }

                Spacer(Modifier.height(12.dp))

                // ── Instructor row ────────────────────────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = instructorImageUrl,
                        contentDescription = instructorName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Instructor",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = instructorName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    // Price
                    Text(
                        text = price,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangeAccent
                    )
                }
            }
        }
    }
}

