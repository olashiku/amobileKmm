package com.exquisite.a_mobile_kmm.feature.auth.presenter.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.core.screen_components.LineButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource


@Composable
fun OnboardScreen(modifier: Modifier = Modifier, goToNextPage: () -> Unit) {

    val pagerState = rememberPagerState(pageCount = { 3 })
    val onboardingData = getOnboardingData()
    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(onboardingData[page].image),
                    contentDescription = "onboardingImage",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(23.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row {
                repeat(pagerState.pageCount) { index ->
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .clip(CircleShape)
                            .padding(2.dp)
                            .width(
                                if (pagerState.currentPage == index) 32.dp else
                                    8.dp
                            )
                            .background(
                                if (pagerState.currentPage == index) Color(0xFFF09103)
                                else Color(0xFFD8D8D8)
                            )
                            .clickable { scope.launch { pagerState.animateScrollToPage(index) } }
                    )
                }
            }

            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = onboardingData[pagerState.currentPage].title,
                style = getPoppinsSemiBold18(),
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF)
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = onboardingData[pagerState.currentPage].description,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF)
            )
            Spacer(modifier = modifier.height(16.dp))
            PrimaryButton(
                "Next",
                {
                    scope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            goToNextPage()
                        }
                    }
                })
            Spacer(modifier = modifier.height(8.dp))
            LineButton("Skip",
                {
                    goToNextPage()
                }
            )
            Spacer(modifier = modifier.height(20.dp))
        }
    }
}