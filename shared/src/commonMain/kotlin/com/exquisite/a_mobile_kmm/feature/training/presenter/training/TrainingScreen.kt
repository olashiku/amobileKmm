package com.exquisite.a_mobile_kmm.feature.training.presenter.training

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.nav.NavigationUtils
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.EmptyState
import com.exquisite.a_mobile_kmm.core.screen_components.SearchTextField
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold24
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.training.domain.model.TrainingCourse
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingScreen( goToCourseDetails: (String) -> Unit,
                    goToTrainingReg: (String) -> Unit,
    viewModel: TrainingViewModel = koinViewModel<TrainingViewModel>(), modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val state = viewModel.trainingState.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(false) }
    var allTrainingCourses by remember { mutableStateOf<List<TrainingCourse>>(emptyList()) }
    var filteredTrainingCourses by remember { mutableStateOf<List<TrainingCourse>>(emptyList()) }

    when (val result = state.value) {
        is TrainingState.Idle -> {}
        is TrainingState.Loading -> {
            isLoading = true
        }

        is TrainingState.Success -> {
            isLoading = false
            allTrainingCourses = result.data.courses
            viewModel.clearState()
        }

        is TrainingState.Error -> {
            isLoading = false
            allTrainingCourses = emptyList()
            snackBar.showError(result.message)
            viewModel.clearState()
        }
    }

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadActiveCoursesAndTraining()
    }

    LaunchedEffect(searchQuery, allTrainingCourses) {
        filteredTrainingCourses = if (searchQuery.isEmpty()) {
            allTrainingCourses
        } else {
            allTrainingCourses.filter { item ->
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true) ||
                item.author.contains(searchQuery, ignoreCase = true)
            }
        }
    }
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
                onValueChange = { query ->
                    searchQuery = query
                },
                placeholder = "Search...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = modifier.height(18.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Available Training & Courses", color = Color(0xff252525),
                    style = getPoppinsBold24()
                )
            }
            Spacer(modifier = modifier.height(15.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                if (isLoading) {
                    repeat(10) {
                        CourseCardSkeleton()
                    }
                } else {
                    if (filteredTrainingCourses.isEmpty()) {
                        EmptyState(
                            if (searchQuery.isEmpty()) "No Training & Courses!" else "No results found",
                            if (searchQuery.isEmpty()) "We will upload training and courses soon" else "Try different search terms"
                        )
                    } else {
                        filteredTrainingCourses.forEach { item ->
                            CourseCard(item,
                                goToCourseDetails = goToCourseDetails,
                                goToTrainingReg = goToTrainingReg

                            )
                        }
                    }
                }
            }
        }

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}

@Composable
fun CourseCardSkeleton(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            // Skeleton cover image with type badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(Color(0xFFE0E0E0))
                )

                // Skeleton type badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .width(80.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFD0D0D0))
                )
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Spacer(Modifier.height(7.dp))

                // Skeleton title
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE0E0E0))
                )

                Spacer(Modifier.height(8.dp))

                // Skeleton duration row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD0D0D0))
                    )
                    Spacer(Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE0E0E0))
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Skeleton instructor row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0))
                    )

                    Spacer(Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE0E0E0))
                        )
                        Spacer(Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE0E0E0))
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE0E0E0))
                    )
                }
            }
        }
    }
}

private val CardBg = Color(0xFFFDF8EE)   // warm cream background

@Composable
fun CourseCard(
    trainingCourse: TrainingCourse,
    goToCourseDetails : (String) -> Unit,
    goToTrainingReg : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth().clickable{
            if (trainingCourse.type.lowercase() == "course")
                goToCourseDetails(NavigationUtils.encodeObject(trainingCourse))
            else
                goToTrainingReg(NavigationUtils.encodeObject(trainingCourse))
        }
    ) {
        Column {

            // ── Cover image with type badge ───────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                AsyncImage(
                    model = trainingCourse.bannerImageUrl,
                    contentDescription = trainingCourse.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                )

                // Type badge overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            color = when (trainingCourse.type.lowercase()) {
                                "course" -> Color(0xFFF1BF0C)
                                "training" -> Color(0xFF2196F3)
                                else -> Color(0xFFF09103)
                            },
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = trainingCourse.type.uppercase(),
                        style = getPoppinsRegular12(),
                        color = Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {

                Spacer(Modifier.height(7.dp))

                // ── Title ─────────────────────────────────────────────────────
                Text(
                    text = trainingCourse.title.lowercase().replaceFirstChar { it.titlecase() },
                    style = getPoppinsMedium14(),
                    color = Color(0xFF252525),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                // ── Duration (if available) ───────────────────────────────────
                if (trainingCourse.numberOfDays != null && trainingCourse.numberOfDays > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF09103))
                        ) {
                            Text("⏱", fontSize = 11.sp)
                        }
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "${trainingCourse.numberOfDays} ${if (trainingCourse.numberOfDays == 1) "Day" else "Days"}",
                            style = getPoppinsRegular14(),
                            color = Color(0xFF666666)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                } else {
                    Spacer(Modifier.height(8.dp))
                }
                // ── Instructor row ────────────────────────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = trainingCourse.authorImageUrl,
                        contentDescription = trainingCourse.author,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.width(10.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Instructor",
                            style = getPoppinsRegular14(),
                            color = Color(0xFF252525),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = trainingCourse.author.lowercase().replaceFirstChar { it.titlecase() },
                            style = getPoppinsSemiBold14(),
                            color = Color(0xFF252525),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Price
                    Text(
                        text = "₦${trainingCourse.amount.formatBalance()}",
                        style = getPoppinsSemiBold16(),
                        color = Color(0xFFF09103),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

