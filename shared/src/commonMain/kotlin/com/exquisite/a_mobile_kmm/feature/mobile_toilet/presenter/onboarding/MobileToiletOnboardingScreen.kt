package com.exquisite.a_mobile_kmm.feature.mobile_toilet.presenter.onboarding

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.exquisite.a_mobile_kmm.core.screen_components.LineButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsLight14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium28
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.auth.presenter.onboard.getOnboardingData
import com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model.getToiletOnboardingData
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun  MobileToiletOnboardingScreen(modifier: Modifier = Modifier,goBack: () -> Unit, goToNextPage: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { getToiletOnboardingData().size })
    val onboardingData = getToiletOnboardingData()
    val scope = rememberCoroutineScope()

    Box(
    modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.align(Alignment.TopCenter)

        ){
            Image(
                painter = painterResource(Res.drawable.back_arrow),
                contentDescription = "Back arrow",
                modifier = modifier.padding(top =50.dp, start = 30.dp).clickable{
                    goBack()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()){
                    Text(onboardingData[index].title, style = getPoppinsMedium28(), color = Color(0xFF252525), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(44.dp))
                    Image(painter = painterResource(onboardingData[index].image), contentDescription = "onboardingImage", modifier = Modifier.width(342.dp).height(334.dp), contentScale = ContentScale.Crop)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(onboardingData[index].description, style = getPoppinsLight14(), color = Color(0xFF252525), textAlign = TextAlign.Center)
                }
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

            Spacer(modifier = Modifier.height(29.dp))
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
                }, textColor = Color(0xFF252525)
            )
            Spacer(modifier = modifier.height(10.dp))
        }
    }
}