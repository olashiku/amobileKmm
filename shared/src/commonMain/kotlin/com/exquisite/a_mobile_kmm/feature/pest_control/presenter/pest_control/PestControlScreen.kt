package com.exquisite.a_mobile_kmm.feature.pest_control.presenter.pest_control

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.warning_icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.GenericAlertModal
import com.exquisite.a_mobile_kmm.core.screen_components.ModalButton
import com.exquisite.a_mobile_kmm.core.screen_components.ModalType
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.screen_components.RadioOptionGroup
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.cleaningTypeOptions
import com.exquisite.a_mobile_kmm.feature.pest_control.domain.model.pestControlOption
import com.exquisite.dripp.core.components.CustomSnackbarHost

@Composable
fun  PestControlScreen(goBack: () -> Unit,
                       goToNextPage:(String)->Unit,
                       modifier: Modifier = Modifier) {

    var selectedCleaningOption by remember { mutableStateOf("residential") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Pest Control Service",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "Kindly select your pest control option",
                    style = getPoppinsRegular14(), color = Color(0xFF252525)
                )
                Spacer(modifier = Modifier.height(16.dp))
                RadioOptionGroup(
                    options = pestControlOption,
                    selectedOptionId = selectedCleaningOption,
                    onOptionSelected = { option ->
                        selectedCleaningOption = option.id
                    },
                    titleStyle = getPoppinsMedium14(),
                    subtitleStyle = getPoppinsRegular12()
                )
            }
        }

        PrimaryButton("Continue", {
            goToNextPage.invoke(selectedCleaningOption)
        }, modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp))

    }
}