package com.exquisite.a_mobile_kmm.feature.cleaning_service.presenter.deep_cleaning_price

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.screenUtils.formatBalance
import com.exquisite.a_mobile_kmm.core.screen_components.Badge
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold32
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningPriceModel
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningSummaryData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.DeepCleaningFormData
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.getCleaningSummaryData
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.rememberSnackBar

@Composable
fun DeepCleaningPriceDetailsScreen(
    deepCleaningFormData: DeepCleaningFormData,
    cleaningPriceModel: CleaningPriceModel, goBack: () -> Unit,goToNextPage: () -> Unit, modifier: Modifier = Modifier,
) {

    val (snackBar, snackBarHostState) = rememberSnackBar()
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Deep Cleaning Summary",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth().background(Color(0XFFFFFFFF)),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0XFFFFFFFF))
                ) {
                    Column(
                        modifier = modifier.fillMaxWidth().wrapContentHeight()
                            .background(Color(0XFFFFF9F0)).padding(20.dp)
                    ) {
                        Spacer(modifier = modifier.height(1.dp))
                        Badge("DEEP CLEANING")
                        Spacer(modifier = modifier.height(8.dp))
                        Text(
                            text = "${
                                cleaningPriceModel.cleaningType.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                            } service",style = getPoppinsSemiBold18(),color =Color(0XFF1A1A1A))
                    }
                    Column(modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            getCleaningSummaryData(deepCleaningFormData).forEach { item ->
                                CleaningSummaryItem(item)
                            }
                        }
                        Spacer(modifier = modifier.height(16.dp))
                        HorizontalDivider( thickness = 1.dp, color = Color(0XFFE2E8F0))
                        Spacer(modifier = modifier.height(20.dp))
                        Row {
                            Text(
                                text = "Address", color = Color(0xFF64748B),
                                style = getPoppinsSemiBold13(),
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = deepCleaningFormData.address?.first?:"",
                                style = getPoppinsMedium13(),
                                color = Color(0xFF1A1A1A), fontSize = 13.sp)
                        }
                        Spacer(modifier = modifier.height(36.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier= modifier.fillMaxWidth()){
                            Text(text = "Estimated Total",
                                style = getPoppinsRegular14(),
                                color = Color(0xFF64748B), fontSize = 14.sp)
                            Spacer(modifier = modifier.height(8.dp))

                            Text(text = "₦${cleaningPriceModel.amount.formatBalance()}",
                                style = getPoppinsBold32(),
                                color = Color(0xFFF29100))
                            Spacer(modifier = modifier.height(24.dp))
                        }


                    }
                }
                Spacer(modifier = modifier.height(12.dp))

                Text(text = "Review your details before continuing",
                    style = getPoppinsRegular14(),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF64748B), fontSize = 14.sp, modifier = modifier.fillMaxSize())
            }
        }
        PrimaryButton("Confirm and Continue", {
            goToNextPage.invoke()
        }, modifier = Modifier.align(BottomCenter).padding(20.dp))

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }
}

@Composable
fun CleaningSummaryItem(deepCleaningFormData: CleaningSummaryData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = deepCleaningFormData.title,
            color = Color(0xFF64748B),
            style = getPoppinsSemiBold13(),
            fontSize = 13.sp,
            modifier = Modifier.weight(0.4f, fill = false)
        )
        Text(
            text = deepCleaningFormData.description,
            style = getPoppinsMedium13(),
            color = Color(0xFF1A1A1A),
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.6f, fill = false)
        )
    }
}