package com.exquisite.a_mobile_kmm.core.screen_components

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.email_icon
import amobilekmm.shared.generated.resources.empty_state_list
import amobilekmm.shared.generated.resources.hide_password
import amobilekmm.shared.generated.resources.password_icon
import amobilekmm.shared.generated.resources.show_password
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DashboardModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun LineButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(0.5.dp, Color(0xFFF09103), RoundedCornerShape(10.dp))
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFFFFFFF)
        )
    }
}

@Composable
fun LineButtonBlackText(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(0.5.dp, Color(0xFFF09103), RoundedCornerShape(10.dp))
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF000000)
        )
    }
}

@Composable
fun QuantityCounter(
    modifier: Modifier = Modifier,
    initialQuantity: Int = 1,
    onQuantityChange: (Int) -> Unit = {}
) {
    var quantity by remember { mutableStateOf(initialQuantity) }

    Row(
        modifier = modifier
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Minus Button
        Box(
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    if (quantity > 1) {
                        quantity--
                        onQuantityChange(quantity)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center
            )
        }

        // Quantity Display
        Text(
            text = quantity.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.width(35.dp),
            textAlign = TextAlign.Center
        )

        // Plus Button
        Box(
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    quantity++
                    onQuantityChange(quantity)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PrimaryButtonWithIcon(text: String,image:DrawableResource, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFF09103),
            Color(0xFFF09103)
        )
    )

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = gradientBrush,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Image(painter = painterResource(image), contentDescription = "icon")
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFF09103),
            Color(0xFFF09103)
        )
    )

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = gradientBrush,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    backgroundColor: Color = Color(0xFFFFFFFF),
    borderColor: Color = Color(0xFFF4F4F4),
    textColor: Color = Color.Black,
    placeholderColor: Color = Color.Gray
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Search Icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.padding(end = 12.dp)
        )

        // Text Input Field
        if (value.isEmpty()) {
            androidx.compose.material3.Text(
                text = placeholder,
                color = placeholderColor,
                style = getPoppinsRegular18(),
                modifier = Modifier.padding(start = 40.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp),
            textStyle = getPoppinsRegular18().copy(
                color = textColor
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                innerTextField()
            }
        )
    }
}

@Composable
fun ValidatedTextField(
    labelText: String,
    placeHolder: String,
    fieldValidator: FieldValidator,
    defaultText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    leadingIconRes: DrawableResource = Res.drawable.email_icon,
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true
) {
    val localColorsPalette = LocalColorsPalette.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Update field value when defaultText changes from external source
    LaunchedEffect(defaultText) {
        if (fieldValidator.value.value != defaultText) {
            fieldValidator.setValue(defaultText)
        }
    }

    val borderColor = when {
        !fieldValidator.isValid.value && fieldValidator.hasBeenTouched.value -> Color.Red
        isFocused -> localColorsPalette.focusedBorderColor
        else -> Color.Transparent
    }

    Column {
        TextField(
            value = fieldValidator.value.value,
            singleLine = true,
            onValueChange = { fieldValidator.setValue(it) },
            label = { Text(labelText) },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.Black
            ),
            placeholder = { Text(placeHolder, color = Color(0xFF5A5A5A)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(leadingIconRes),
                    contentDescription = "$labelText icon",
                    tint = Color(0xFFA0A0A0)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            interactionSource = interactionSource,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF6F6F6),
                unfocusedContainerColor = Color(0xFFF6F6F6),
                disabledContainerColor = Color(0xFFF6F6F6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color(0xFF5A5A5A),
                unfocusedPlaceholderColor = Color(0xFF5A5A5A)
            ),
            modifier = if (fillMaxWidth) {
                modifier
                    .fillMaxWidth()
                    .border(
                        width = if (borderColor != Color.Transparent) 2.dp else 0.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            } else {
                modifier
                    .border(
                        width = if (borderColor != Color.Transparent) 2.dp else 0.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            }
        )

        if (!fieldValidator.isValid.value && fieldValidator.hasBeenTouched.value) {
            Text(
                text = fieldValidator.errorMessage.value,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Composable
fun ValidatedPasswordTextField(
    labelText: String,
    placeHolder: String,
    fieldValidator: FieldValidator,
    modifier: Modifier = Modifier
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val localColorsPalette = LocalColorsPalette.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor = when {
        !fieldValidator.isValid.value && fieldValidator.hasBeenTouched.value -> Color.Red
        isFocused -> localColorsPalette.focusedBorderColor
        else -> Color.Transparent
    }

    Column {
        TextField(
            value = fieldValidator.value.value,
            singleLine = true,
            onValueChange = { fieldValidator.setValue(it) },
            label = { Text(labelText) },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.Black
            ),
            placeholder = { Text(placeHolder, color = Color(0xFF5A5A5A)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.password_icon),
                    contentDescription = "Lock icon",
                    tint = Color(0xFFA0A0A0)
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible.value)
                                Res.drawable.hide_password
                            else
                                Res.drawable.show_password,

                            ),
                        contentDescription = if (passwordVisible.value)
                            "Hide password"
                        else
                            "Show password",
                        tint = Color(0xFFA0A0A0)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            interactionSource = interactionSource,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF6F6F6),
                unfocusedContainerColor = Color(0xFFF6F6F6),
                disabledContainerColor = Color(0xFFF6F6F6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color(0xFF5A5A5A),
                unfocusedPlaceholderColor = Color(0xFF5A5A5A)
            ),
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = if (borderColor != Color.Transparent) 2.dp else 0.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        if (!fieldValidator.isValid.value && fieldValidator.hasBeenTouched.value) {
            Text(
                text = fieldValidator.errorMessage.value,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Composable
fun PrimaryCheckBox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isChecked = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFF09103),
            modifier = modifier.clickable { isChecked.value = !isChecked.value }
        )
    }
}

@Composable
fun PrimaryCheckBox(
    composable: @Composable () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isChecked = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        composable.invoke()

    }
}

@Composable
fun OtpTextField(
    onOtpTextChange: (String) -> Unit,
    otpLength: Int = 6,
    modifier: Modifier = Modifier
) {
    var otpText by remember { mutableStateOf("") }
    val localColorsPalette = LocalColorsPalette.current

    LaunchedEffect(otpText) {
        onOtpTextChange(otpText)
    }

    BasicTextField(
        value = otpText,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || (newValue.length <= otpLength && newValue.all { it.isDigit() })) {
                otpText = newValue
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        modifier = modifier
            .wrapContentWidth()
            .semantics { contentDescription = "Enter $otpLength digit verification code" },
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                repeat(otpLength) { index ->
                    val char = otpText.getOrNull(index)?.toString() ?: ""
                    val isFocused = index == otpText.length

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .border(
                                width = 2.dp,
                                color = when {
                                    isFocused -> localColorsPalette.focusedBorderColor
                                    char.isNotEmpty() -> localColorsPalette.titleColor
                                    else -> Color.Gray.copy(alpha = 0.5f)
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(
                                color = Color(0xFF2D2D2D),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = MaterialTheme.typography.headlineMedium,
                            color = localColorsPalette.titleColor
                        )
                    }
                }
            }

            // Hidden text field for proper input handling
            Box(
                modifier = Modifier
                    .size(0.dp)
            ) {
                innerTextField()
            }
        }
    )
}


@Composable
fun LinerBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier =
            modifier.clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color(0XFFF4F4F4), RoundedCornerShape(10.dp))
    ) {
        content()
    }
}

@Composable
fun MenuItem(dashboardModal: DashboardModel, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(5.dp)
    ) {
        Image(painter = painterResource(dashboardModal.image), contentDescription = "menu_icon")
        Text(
            text = dashboardModal.title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyState(title: String, body: String, modifier: Modifier = Modifier) {
    Box {
        Column(
            modifier = modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(20.dp))
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = Color(0xFF000000),
                    spotColor = Color(0xFFF8E8E8)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.empty_state_list),
                contentDescription = "empty_state"
            )
            Text(
                text = title.uppercase(),
                color = Color(0xFF252525),
                style = getPoppinsSemiBold18()
            )
            Spacer(modifier = modifier.height(2.dp))
            Text(text = body, color = Color(0xFF252525), style = getPoppinsRegular12())
        }
    }


}

@Composable
fun Banner(
    bannerColor: Color = Color(0xFFF09103),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(28.dp)
                .background(
                    color = bannerColor,
                    shape = RoundedCornerShape(2.dp)
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        content()
    }
}

@Composable
fun AvatarIcon(
    size: Dp,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFFFF8C00),
    backgroundColor: Color = Color(0xffFFF4E5),

    ) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .background(backgroundColor)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val dotSize = 30f
                drawCircle(
                    color = borderColor,
                    radius = center.x - strokeWidth,
                    style = Stroke(
                        width = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(dotSize, dotSize), // dot, gap
                            phase = 0f
                        )
                    )
                )
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFF8C00),
            modifier = Modifier.size(size * 0.5f)
        )

    }
}


sealed class ModalType {
    data class Success(val iconRes: org.jetbrains.compose.resources.DrawableResource) : ModalType()
    data class Error(val iconRes: org.jetbrains.compose.resources.DrawableResource) : ModalType()
    data class Warning(val iconRes: org.jetbrains.compose.resources.DrawableResource) : ModalType()
    data class Confirmation(val iconRes: org.jetbrains.compose.resources.DrawableResource) : ModalType()
}

data class ModalButton(
    val text: String,
    val backgroundColor: Color,
    val textColor: Color = Color.White,
    val borderColor: Color? = null,
    val action: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericAlertModal(
    modalType: ModalType,
    title: String,
    message: String,
    primaryButton: ModalButton,
    secondaryButton: ModalButton? = null,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val iconRes = when (modalType) {
        is ModalType.Success -> modalType.iconRes
        is ModalType.Error -> modalType.iconRes
        is ModalType.Warning -> modalType.iconRes
        is ModalType.Confirmation -> modalType.iconRes
    }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = Color(0xFF1A1A1A),
        contentColor = Color.White
    ) {
        Column(
            modifier = modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = title,
                style = getPoppinsBold18(),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Message
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF525252),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Primary Button
            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        primaryButton.action()
                    }
                },
                shape = RoundedCornerShape(25.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = primaryButton.backgroundColor
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp).let {
                    if (primaryButton.borderColor != null) {
                        it.border(
                            width = 1.5.dp,
                            color = primaryButton.borderColor,
                            shape = RoundedCornerShape(25.dp)
                        )
                    } else it
                }
            ) {
                Text(
                    text = primaryButton.text,
                    color = primaryButton.textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Secondary Button (optional)
            secondaryButton?.let { button ->
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            button.action()
                        }
                    },
                    shape = RoundedCornerShape(25.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = button.backgroundColor
                    ),
                    modifier = Modifier.fillMaxWidth().height(56.dp).let {
                        if (button.borderColor != null) {
                            it.border(
                                width = 1.5.dp,
                                color = button.borderColor,
                                shape = RoundedCornerShape(25.dp)
                            )
                        } else it
                    }
                ) {
                    Text(
                        text = button.text,
                        color = button.textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


