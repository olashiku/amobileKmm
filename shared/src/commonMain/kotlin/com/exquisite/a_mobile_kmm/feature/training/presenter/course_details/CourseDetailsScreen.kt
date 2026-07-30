package com.exquisite.a_mobile_kmm.feature.training.presenter.course_details

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.platformUtils.openUrl
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold24
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCourse

@Composable
fun CourseDetailsScreen(
    trainingCourse:TrainingCourse,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            FixedHeaderWithBackButton(
                title = "Course Details",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp)
            ) {
                // Hero Banner Image
                AsyncImage(
                    model = trainingCourse.bannerImageUrl,
                    contentDescription = trainingCourse.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )

                // Course Details Card
                Card(
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 30.dp)
                    ) {
                        // Instructor Chip
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = trainingCourse.authorImageUrl,
                                contentDescription = trainingCourse.author,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "INSTRUCTOR",
                                    style = getPoppinsRegular14(),
                                    color = Color(0xFF717680),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = trainingCourse.author,
                                    style = getPoppinsSemiBold16(),
                                    color = Color(0xFF1A1D1E)
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        // Course Title
                        Text(
                            text = trainingCourse.title,
                            style = getPoppinsBold24(),
                            color = Color(0xFF1A1D1E),
                            lineHeight = 32.sp
                        )

                        Spacer(Modifier.height(25.dp))

                        // Description Label
                        Text(
                            text = "DESCRIPTION",
                            style = getPoppinsSemiBold14(),
                            color = Color(0xFF1A1D1E),
                            fontSize = 14.sp,
                            letterSpacing = 0.5.sp
                        )

                        Spacer(Modifier.height(10.dp))

                        // Description Content
                        Text(
                            text = trainingCourse.description,
                            style = getPoppinsRegular14(),
                            color = Color(0xFF717680),
                            lineHeight = 25.sp
                        )

                        Spacer(Modifier.height(30.dp))
                    }
                }
            }
        }

        // Sticky Footer
        Card(
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Price Column
                Column {
                    Text(
                        text = "TOTAL FEE",
                        style = getPoppinsSemiBold14(),
                        color = Color(0xFF717680),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "₦${trainingCourse.amount.formatBalance()}",
                        style = getPoppinsBold24(),
                        color = Color(0xFF1A1D1E)
                    )
                }

                Spacer(modifier = modifier.width(30.dp))
                // Register Button
                PrimaryButton(
                    text = "Register",
                    onClick = {
                    openUrl(trainingCourse.resourceLink?:"")
                    },

                )
            }
        }
    }
}


