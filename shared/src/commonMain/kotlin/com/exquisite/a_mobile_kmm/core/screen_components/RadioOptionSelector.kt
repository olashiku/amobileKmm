package com.exquisite.a_mobile_kmm.core.screen_components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.text.TextStyle

/**
 * Data class representing a selectable option
 * @param id Unique identifier for the option
 * @param title Main text to display
 * @param subtitle Optional secondary text (e.g., wallet balance)
 */
data class RadioOption(
    val id: String,
    val title: String,
    val subtitle: String? = null
)

/**
 * Single radio option item with customizable styling
 *
 * @param option The option data to display
 * @param isSelected Whether this option is currently selected
 * @param onClick Callback when the option is clicked
 * @param modifier Modifier for the row
 * @param selectedColor Color when selected
 * @param unselectedColor Color when not selected
 * @param titleStyle Text style for the title
 * @param subtitleStyle Text style for the subtitle (if present)
 */
@Composable
fun RadioOptionItem(
    option: RadioOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color(0xFFF09103),
    unselectedColor: Color = Color(0xFFD9D9D9),
    titleStyle: TextStyle = TextStyle.Default,
    subtitleStyle: TextStyle = TextStyle.Default
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Radio button indicator
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = if (isSelected) selectedColor else unselectedColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = selectedColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Text content
        Column {
            Text(
                text = option.title,
                style = titleStyle,
                color = Color(0xFF252525)
            )
            option.subtitle?.let { subtitle ->
                Text(
                    text = subtitle,
                    style = subtitleStyle,
                    color = Color(0xFF7D7D7D)
                )
            }
        }
    }
}

/**
 * Group of radio options with single selection
 *
 * @param options List of available options
 * @param selectedOptionId Currently selected option ID
 * @param onOptionSelected Callback when an option is selected
 * @param modifier Modifier for the column
 * @param selectedColor Color for selected state
 * @param unselectedColor Color for unselected state
 * @param titleStyle Text style for titles
 * @param subtitleStyle Text style for subtitles
 */
@Composable
fun RadioOptionGroup(
    options: List<RadioOption>,
    selectedOptionId: String?,
    onOptionSelected: (RadioOption) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color(0xFFF09103),
    unselectedColor: Color = Color(0xFFD9D9D9),
    titleStyle: TextStyle = TextStyle.Default,
    subtitleStyle: TextStyle = TextStyle.Default
) {
    Column(modifier = modifier) {
        options.forEach { option ->
            RadioOptionItem(
                option = option,
                isSelected = option.id == selectedOptionId,
                onClick = { onOptionSelected(option) },
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                titleStyle = titleStyle,
                subtitleStyle = subtitleStyle
            )
        }
    }
}
