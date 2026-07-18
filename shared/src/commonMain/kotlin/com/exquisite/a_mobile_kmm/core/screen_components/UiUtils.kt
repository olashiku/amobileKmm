package com.exquisite.a_mobile_kmm.core.screen_components

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.empty_state_list
import amobilekmm.shared.generated.resources.hide_password
import amobilekmm.shared.generated.resources.password_icon
import amobilekmm.shared.generated.resources.show_password
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.screenUtils.FieldValidator
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold11
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsBold20
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium10
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular18
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold13
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.home_and_ecommerce.domain.model.DashboardModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlinx.datetime.*
import kotlinx.serialization.Serializable


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
    leadingIconRes: DrawableResource? = null,
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
            leadingIcon = leadingIconRes?.let {
                {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "$labelText icon",
                        tint = Color(0xFFA0A0A0)
                    )
                }
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
fun ValidatedDropdownField(
    labelText: String,
    placeHolder: String,
    fieldValidator: FieldValidator,
    options: List<String>,
    defaultText: String = "",
    leadingIconRes: DrawableResource? = null,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onSelectionChange: ((String) -> Unit)? = null
) {
    val localColorsPalette = LocalColorsPalette.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var expanded by remember { mutableStateOf(false) }

    // Update field value when defaultText changes from external source
    LaunchedEffect(defaultText) {
        if (defaultText.isNotEmpty() && fieldValidator.value.value != defaultText) {
            fieldValidator.setValue(defaultText)
        }
    }

    val borderColor = when {
        !fieldValidator.isValid.value && fieldValidator.hasBeenTouched.value -> Color.Red
        isFocused || expanded -> localColorsPalette.focusedBorderColor
        else -> Color.Transparent
    }

    Column {
        Box(modifier = modifier.fillMaxWidth()) {
            TextField(
                value = fieldValidator.value.value,
                onValueChange = { },
                readOnly = true,
                label = { Text(labelText) },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                placeholder = { Text(placeHolder, color = Color(0xFF5A5A5A)) },
                leadingIcon = leadingIconRes?.let {
                    {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = "$labelText icon",
                            tint = Color(0xFFA0A0A0)
                        )
                    }
                },
                trailingIcon = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    } else {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown arrow",
                                tint = Color(0xFFA0A0A0)
                            )
                        }
                    }
                },
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .border(
                        width = if (borderColor != Color.Transparent) 2.dp else 0.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(0xFFF6F6F6))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        },
                        onClick = {
                            fieldValidator.setValue(option)
                            onSelectionChange?.invoke(option)
                            expanded = false
                        }
                    )
                }
            }
        }

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
fun MenuItem(dashboardModal: DashboardModel,goToMenuItem: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(5.dp).clickable{
            goToMenuItem(dashboardModal.label)
        }
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
fun EmptyState(title: String, body: String,imageDrawable: DrawableResource = Res.drawable.empty_state_list, modifier: Modifier = Modifier) {
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
                painter = painterResource(imageDrawable),
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
    data class Success(val iconRes: DrawableResource) : ModalType()
    data class Error(val iconRes: DrawableResource) : ModalType()
    data class Warning(val iconRes: DrawableResource) : ModalType()
    data class Confirmation(val iconRes: DrawableResource) : ModalType()
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
        containerColor = Color(0xFFFFFFFF),
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
                color = Color(0XFF232323)
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF09103)
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

@Composable
fun Badge(title:String) {
    Text(
        text = title,
        color = Color(0xFFE65100), // terracotta/orange text
        style = getPoppinsBold11(),
        modifier = Modifier
            .background(
                color = Color(0xFFFFE0B2), // pale cream/yellow background
                shape = RoundedCornerShape(6.dp)
            )
            .padding(5.dp)
    )
}

@Serializable
data class DateModel(
    val dayName: String,
    val dayNumber: String,
    val fullDate: String, // Use this for logic/API calls
    val isSelected: Boolean = false
)



fun generateAvailableDates(daysCount: Int = 14): List<DateModel> {
    val dates = mutableListOf<DateModel>()

    // Get the current date in the system's default time zone
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    for (i in 0 until daysCount) {
        val date = today.plus(i, DateTimeUnit.DAY)

        dates.add(
            DateModel(
                // We take the first 3 letters of the day name (e.g., "MONDAY" -> "MON")
                dayName = date.dayOfWeek.name.take(3).uppercase(),

                dayNumber = date.dayOfMonth.toString(),

                // Manual ISO format (yyyy-MM-dd)
                fullDate = "${date.year}-${date.monthNumber.toString().padStart(2, '0')}-${date.dayOfMonth.toString().padStart(2, '0')}"
            )
        )
    }
    return dates
}

@Composable
fun DateCard(
    date: DateModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .height(74.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFF29100) else Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFF29100) else Color(0xFFE2E8F0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayName,
            style = getPoppinsMedium12(),
            color = if (isSelected) Color.White else Color(0xFF64748B)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = date.dayNumber,
            style = getPoppinsMedium18(),
            color = if (isSelected) Color.White else Color(0xFF1E293B)
        )
    }
}
@Composable
fun HybridDatePicker(
    dates: List<DateModel>,
    selectedDate: DateModel?,
    onDateSelected: (DateModel) -> Unit,
    onOpenFullCalendar: () -> Unit // Trigger for the DatePickerDialog
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. The Quick-Select Slider
        items(dates) { date ->
            DateCard(date, isSelected = (date == selectedDate)) {
                onDateSelected(date)
            }
        }

        // 2. The "Future Date" Button
        item {
            Column(
                modifier = Modifier
                    .width(60.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF1F5F9)) // Neutral gray
                    .clickable { onOpenFullCalendar() },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange, // Use a Calendar Icon
                    contentDescription = "Select Future Date",
                    tint = Color(0xFF64748B)
                )
                Text(
                    text = "More",
                    style = getPoppinsMedium10(),
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
fun TimeSlotGrid(
    timeSlots: List<String>,
    selectedTime: String?,
    onTimeSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(timeSlots) { time ->
            val isSelected = time == selectedTime

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color(0xFFFFF7ED) else Color.White)
                    .border(1.dp, if (isSelected) Color(0xFFF29100) else Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                    .clickable { onTimeSelected(time) }
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time,
                    style = getPoppinsMedium18(),
                    color = if (isSelected) Color(0xFFF29100) else Color(0xFF1E293B)
                )
            }
        }
    }
}

@Composable
fun OptionCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color(0xFFFFF6E9) // warm cream/peach
    val borderColor = Color(0xFFF5E6C8)
    val subtitleColor = Color(0xFF8A8A8E)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(20.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 20.dp, vertical = 18.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = getPoppinsRegular14(),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = getPoppinsRegular12(),
                color = subtitleColor
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Custom square checkbox to match the rounded-square look in the design
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(if (checked) Color(0xFF2F6F4F) else Color.White)
                .border(
                    BorderStroke(1.5.dp, if (checked) Color(0xFF2F6F4F) else Color(0xFFD8D8DC)),
                    RoundedCornerShape(7.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
fun ImageGrid(
    services: List<String>,
    modifier: Modifier = Modifier,
    deleteImage: (String) -> Unit
) {
    val columns = 3
    val cardHeight = 88.dp
    val horizontalSpacing = 8.dp
    val verticalSpacing = 12.dp
    val rows = (services.size + columns - 1) / columns
    val totalHeight = (cardHeight * rows) + (verticalSpacing * (rows - 1).coerceAtLeast(0))

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.height(totalHeight),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        userScrollEnabled = false
    ) {
        items(services.size) { index ->
            ImageItem(
                imageUrl = services[index],
                deleteImage = deleteImage
            )
        }
    }
}

@Composable
fun ImageItem(
    imageUrl: String,
    deleteImage: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(88.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE8E8E8),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(6.dp)
    ) {
        // Image
        AsyncImage(
            model = imageUrl,
            contentDescription = "Captured image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        // Close button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF3E0))
                .clickable { deleteImage(imageUrl) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Remove image",
                tint = Color(0xFFF09103),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}


@Composable
fun InfoBanner(
    message: String,
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFFE8A33D),
    backgroundColor: Color = Color(0xFFFFF8EC),
    iconBackgroundColor: Color = Color(0xFFE8A33D),
    textColor: Color = Color(0xFF252525)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .dashedBorder(
                color = borderColor,
                strokeWidth = 1.5.dp,
                dashLength = 8.dp,
                gapLength = 6.dp,
                cornerRadius = 16.dp
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "i",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Text(
                text = message,
                color = textColor,
               style = getPoppinsMedium10(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// Custom dashed border modifier
fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp,
    dashLength: Dp,
    gapLength: Dp,
    cornerRadius: Dp
) = this.drawBehind {
    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
        )
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(cornerRadius.toPx())
    )
}

@Composable
fun MonthlyCalendarSelector(
    selectedDates: Set<Int>,
    onDatesChanged: (Set<Int>) -> Unit,
    month: String,
    year: Int,
    daysInMonth: Int,
    firstDayOfWeek: Int,
    onMonthChange: ((Int, Int) -> Unit)? = null, // (year, month) callback for navigation
    modifier: Modifier = Modifier,
    minRequiredDays: Int = 6,
    excludeSundays: Boolean = true,
    excludePastDates: Boolean = false,
    currentMonth: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.monthNumber
) {
    val dayHeaders = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val isValidSelection = selectedDates.size >= minRequiredDays
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF8EC), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Month and Year Header with optional navigation
        if (onMonthChange != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Month Button
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            val monthNum = kotlinx.datetime.Month.values()
                                .indexOfFirst { it.name.lowercase() == month.lowercase() } + 1
                            if (monthNum == 1) {
                                onMonthChange(year - 1, 12)
                            } else {
                                onMonthChange(year, monthNum - 1)
                            }
                        },
                    tint = Color(0xFF1A1A1A)
                )

                // Month and Year Text
                Text(
                    text = "$month $year",
                    style = getPoppinsSemiBold18(),
                    color = Color(0xFF1A1A1A),
                    textAlign = TextAlign.Center
                )

                // Next Month Button
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            val monthNum = kotlinx.datetime.Month.values()
                                .indexOfFirst { it.name.lowercase() == month.lowercase() } + 1
                            if (monthNum == 12) {
                                onMonthChange(year + 1, 1)
                            } else {
                                onMonthChange(year, monthNum + 1)
                            }
                        },
                    tint = Color(0xFF1A1A1A)
                )
            }
        } else {
            // Month and Year Header without navigation
            Text(
                text = "$month $year",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        // Day headers (Mon, Tue, Wed, etc.)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dayHeaders.forEach { dayHeader ->
                Text(
                    text = dayHeader,
                    style = getPoppinsMedium14(),
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Calendar grid
        val totalCells = daysInMonth + firstDayOfWeek
        val rows = (totalCells + 6) / 7

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (week in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (dayOfWeek in 0 until 7) {
                        val cellIndex = week * 7 + dayOfWeek
                        val dayNumber = cellIndex - firstDayOfWeek + 1

                        if (cellIndex < firstDayOfWeek || dayNumber > daysInMonth) {
                            // Empty cell
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            val isSunday = dayOfWeek == 6
                            val currentDayDate = LocalDate(year, currentMonth, dayNumber)
                            val isPastDate = excludePastDates && currentDayDate < today
                            val isDisabled = (excludeSundays && isSunday) || isPastDate
                            val isSelected = selectedDates.contains(dayNumber)

                            CalendarDayCell(
                                day = dayNumber,
                                isSelected = isSelected,
                                isDisabled = isDisabled,
                                onClick = {
                                    if (!isDisabled) {
                                        val newSelection = if (selectedDates.contains(dayNumber)) {
                                            selectedDates - dayNumber
                                        } else {
                                            selectedDates + dayNumber
                                        }
                                        onDatesChanged(newSelection)
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        if (!isValidSelection) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Please select at least $minRequiredDays days",
                style = getPoppinsRegular12(),
                color = Color(0xFFD32F2F),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: Int,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(
                if (isSelected) {
                    Modifier.dashedBorder(
                        color = Color(0xFF2196F3),
                        strokeWidth = 1.5.dp,
                        dashLength = 6.dp,
                        gapLength = 4.dp,
                        cornerRadius = 8.dp
                    )
                } else {
                    Modifier
                }
            )
            .background(
                color = when {
                    isDisabled -> Color.Transparent
                    isSelected -> Color(0xFFF5F5F5)
                    else -> Color.White
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !isDisabled, onClick = onClick)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            style = if (isSelected) getPoppinsSemiBold14() else getPoppinsRegular14(),
            color = when {
                isDisabled -> Color(0xFFBDBDBD)
                isSelected -> Color(0xFF2196F3)
                else -> Color(0xFF1A1A1A)
            }
        )
    }
}

@Composable
fun MonthlyCalendarSelector(
    selectedDates: Set<Int>,
    onDatesChanged: (Set<Int>) -> Unit,
    date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    modifier: Modifier = Modifier,
    minRequiredDays: Int = 6,
    excludeSundays: Boolean = true,
    excludePastDates: Boolean = false
) {
    val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = date.year

    // Calculate days in month
    val daysInMonth = when (date.monthNumber) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 30
    }

    // Get first day of month (0 = Monday, 6 = Sunday)
    val firstDayOfMonth = LocalDate(year, date.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    MonthlyCalendarSelector(
        selectedDates = selectedDates,
        onDatesChanged = onDatesChanged,
        month = month,
        year = year,
        daysInMonth = daysInMonth,
        firstDayOfWeek = firstDayOfWeek,
        modifier = modifier,
        minRequiredDays = minRequiredDays,
        excludeSundays = excludeSundays,
        excludePastDates = excludePastDates,
        currentMonth = date.monthNumber
    )
}

// Date Range Selection Data Class
data class DateRange(
    val startDate: Int? = null,
    val endDate: Int? = null,
    val year: Int? = null,
    val month: Int? = null
) {
    fun getSelectedDates(excludeSundays: Boolean = true, year: Int, month: Int): Set<Int> {
        if (startDate == null || endDate == null) return emptySet()

        val start = minOf(startDate, endDate)
        val end = maxOf(startDate, endDate)

        return (start..end).filter { day ->
            if (!excludeSundays) true
            else {
                val date = LocalDate(year, month, day)
                date.dayOfWeek.ordinal != 6 // Exclude Sunday (6 = Sunday)
            }
        }.toSet()
    }

    // Get actual LocalDate objects
    fun getActualDates(excludeSundays: Boolean = true): List<LocalDate> {
        if (startDate == null || endDate == null || year == null || month == null) return emptyList()

        val start = minOf(startDate, endDate)
        val end = maxOf(startDate, endDate)

        return (start..end).mapNotNull { day ->
            val date = LocalDate(year, month, day)
            if (excludeSundays && date.dayOfWeek.ordinal == 6) {
                null // Exclude Sunday
            } else {
                date
            }
        }
    }

    // Get start and end as LocalDate
    fun getStartLocalDate(): LocalDate? {
        return if (startDate != null && year != null && month != null) {
            LocalDate(year, month, startDate)
        } else null
    }

    fun getEndLocalDate(): LocalDate? {
        return if (endDate != null && year != null && month != null) {
            LocalDate(year, month, endDate)
        } else null
    }

    // Get dates as formatted strings "yyyy-MM-dd"
    fun getFormattedDates(excludeSundays: Boolean = true): Set<String> {
        if (startDate == null || endDate == null || year == null || month == null) return emptySet()

        val start = minOf(startDate, endDate)
        val end = maxOf(startDate, endDate)

        return (start..end).mapNotNull { day ->
            val date = LocalDate(year, month, day)
            if (excludeSundays && date.dayOfWeek.ordinal == 6) {
                null // Exclude Sunday
            } else {
                // Format as yyyy-MM-dd
                "${date.year}-${date.monthNumber.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
            }
        }.toSet()
    }
}

@Composable
fun MonthlyCalendarRangeSelector(
    dateRange: DateRange,
    onDateRangeChanged: (DateRange) -> Unit,
    initialDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    modifier: Modifier = Modifier,
    minRequiredDays: Int = 1,
    maxAllowedDays: Int = 6,
    excludeSundays: Boolean = true
) {
    var currentDate by remember { mutableStateOf(initialDate) }
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }

    val month = currentDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = currentDate.year
    val monthNumber = currentDate.monthNumber

    // Calculate days in month
    val daysInMonth = when (monthNumber) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 30
    }

    val selectedDates = dateRange.getSelectedDates(excludeSundays, year, monthNumber)
    val isValidSelection = selectedDates.size >= minRequiredDays && selectedDates.size <= maxAllowedDays

    // Check if both dates are in the same calendar week (Monday-Sunday)
    val isWithinWeek = if (dateRange.startDate != null && dateRange.endDate != null) {
        val startDate = LocalDate(year, monthNumber, dateRange.startDate!!)
        val endDate = LocalDate(year, monthNumber, dateRange.endDate!!)

        // Get the Monday of the week for both dates
        val startWeekMonday = startDate.minus(DatePeriod(days = startDate.dayOfWeek.ordinal))
        val endWeekMonday = endDate.minus(DatePeriod(days = endDate.dayOfWeek.ordinal))

        // Both dates must have the same week's Monday
        startWeekMonday == endWeekMonday
    } else true

    // Get first day of month (0 = Monday, 6 = Sunday)
    val firstDayOfMonth = LocalDate(year, currentDate.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    val dayHeaders = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF8EC), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Month and Year Header with Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous Month Button
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Month",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        currentDate = if (monthNumber == 1) {
                            LocalDate(year - 1, 12, 1)
                        } else {
                            LocalDate(year, monthNumber - 1, 1)
                        }
                    },
                tint = Color(0xFF1A1A1A)
            )

            // Month and Year Text
            Text(
                text = "$month $year",
                style = getPoppinsSemiBold18(),
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center
            )

            // Next Month Button
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Month",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        currentDate = if (monthNumber == 12) {
                            LocalDate(year + 1, 1, 1)
                        } else {
                            LocalDate(year, monthNumber + 1, 1)
                        }
                    },
                tint = Color(0xFF1A1A1A)
            )
        }

        // Day headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dayHeaders.forEach { dayHeader ->
                Text(
                    text = dayHeader,
                    style = getPoppinsMedium14(),
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Calendar grid
        val totalCells = daysInMonth + firstDayOfWeek
        val rows = (totalCells + 6) / 7

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (week in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (dayIndex in 0 until 7) {
                        val cellIndex = week * 7 + dayIndex
                        val dayNumber = cellIndex - firstDayOfWeek + 1

                        if (cellIndex < firstDayOfWeek || dayNumber > daysInMonth) {
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            val currentDayDate = LocalDate(year, monthNumber, dayNumber)
                            val isSunday = dayIndex == 6
                            val isPastDate = currentDayDate < today
                            val isDisabled = (excludeSundays && isSunday) || isPastDate
                            val isSelected = selectedDates.contains(dayNumber)
                            val isStartDate = dateRange.startDate == dayNumber
                            val isEndDate = dateRange.endDate == dayNumber

                            CalendarRangeDayCell(
                                day = dayNumber,
                                isSelected = isSelected,
                                isStartDate = isStartDate,
                                isEndDate = isEndDate,
                                isDisabled = isDisabled,
                                onClick = {
                                    if (!isDisabled) {
                                        when {
                                            dateRange.startDate == null -> {
                                                onDateRangeChanged(DateRange(
                                                    startDate = dayNumber,
                                                    year = year,
                                                    month = monthNumber
                                                ))
                                            }
                                            dateRange.endDate == null -> {
                                                val potentialRange = DateRange(dateRange.startDate, dayNumber, year, monthNumber)
                                                val potentialDates = potentialRange.getSelectedDates(excludeSundays, year, monthNumber)

                                                // Check if both dates are in the same calendar week
                                                val startDateObj = LocalDate(year, monthNumber, dateRange.startDate!!)
                                                val endDateObj = LocalDate(year, monthNumber, dayNumber)

                                                val startWeekMonday = startDateObj.minus(DatePeriod(days = startDateObj.dayOfWeek.ordinal))
                                                val endWeekMonday = endDateObj.minus(DatePeriod(days = endDateObj.dayOfWeek.ordinal))

                                                // Validate range constraints
                                                if (startWeekMonday != endWeekMonday) {
                                                    // Dates are in different weeks - don't allow
                                                    return@CalendarRangeDayCell
                                                } else if (potentialDates.size > maxAllowedDays) {
                                                    // Too many days selected - don't allow
                                                    return@CalendarRangeDayCell
                                                } else {
                                                    onDateRangeChanged(dateRange.copy(
                                                        endDate = dayNumber,
                                                        year = year,
                                                        month = monthNumber
                                                    ))
                                                }
                                            }
                                            else -> {
                                                // Reset and start new selection
                                                onDateRangeChanged(DateRange(
                                                    startDate = dayNumber,
                                                    year = year,
                                                    month = monthNumber
                                                ))
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Range info
        if (dateRange.startDate != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (dateRange.endDate != null) {
                    "Range: ${dateRange.startDate} - ${dateRange.endDate} (${selectedDates.size} days)"
                } else {
                    "Start: ${dateRange.startDate} (Select end date)"
                },
                style = getPoppinsMedium13(),
                color = Color(0xFF2196F3),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Validation messages
        if (dateRange.endDate != null) {
            Spacer(modifier = Modifier.height(8.dp))
            when {
                !isWithinWeek -> {
                    Text(
                        text = "Selection must be within the same week (Mon-Sun)",
                        style = getPoppinsRegular12(),
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                selectedDates.size > maxAllowedDays -> {
                    Text(
                        text = "Maximum $maxAllowedDays days allowed (excluding Sundays)",
                        style = getPoppinsRegular12(),
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                selectedDates.size < minRequiredDays -> {
                    Text(
                        text = "Please select at least $minRequiredDays day(s)",
                        style = getPoppinsRegular12(),
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                else -> {
                    Text(
                        text = "✓ Valid selection: ${selectedDates.size} day(s)",
                        style = getPoppinsRegular12(),
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarRangeDayCell(
    day: Int,
    isSelected: Boolean,
    isStartDate: Boolean,
    isEndDate: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(
                when {
                    isStartDate || isEndDate -> Modifier.border(
                        width = 2.dp,
                        color = Color(0xFF2196F3),
                        shape = RoundedCornerShape(8.dp)
                    )
                    isSelected -> Modifier.dashedBorder(
                        color = Color(0xFF2196F3),
                        strokeWidth = 1.5.dp,
                        dashLength = 6.dp,
                        gapLength = 4.dp,
                        cornerRadius = 8.dp
                    )
                    else -> Modifier
                }
            )
            .background(
                color = when {
                    isDisabled -> Color.Transparent
                    isStartDate || isEndDate -> Color(0xFF2196F3)
                    isSelected -> Color(0xFFE3F2FD)
                    else -> Color.White
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !isDisabled, onClick = onClick)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            style = when {
                isStartDate || isEndDate -> getPoppinsBold14()
                isSelected -> getPoppinsSemiBold14()
                else -> getPoppinsRegular14()
            },
            color = when {
                isDisabled -> Color(0xFFBDBDBD)
                isStartDate || isEndDate -> Color.White
                isSelected -> Color(0xFF2196F3)
                else -> Color(0xFF1A1A1A)
            }
        )
    }
}

@Composable
fun SingleDateCalendarSelector(
    selectedDate: DateModel?,
    onDateSelected: (DateModel?) -> Unit,
    excludeSundays: Boolean = true,
    excludePastDates: Boolean = true,
    modifier: Modifier = Modifier
) {
    var currentDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }

    val month = currentDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = currentDate.year
    val monthNumber = currentDate.monthNumber

    // Calculate days in month
    val daysInMonth = when (monthNumber) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 30
    }

    // Get first day of month (0 = Monday, 6 = Sunday)
    val firstDayOfMonth = LocalDate(year, currentDate.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    val dayHeaders = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    // Helper function to create DateModel from day number
    fun createDateModel(dayNumber: Int): DateModel {
        val date = LocalDate(year, monthNumber, dayNumber)
        return DateModel(
            dayName = date.dayOfWeek.name.take(3).uppercase(),
            dayNumber = dayNumber.toString(),
            fullDate = "${date.year}-${date.monthNumber.toString().padStart(2, '0')}-${dayNumber.toString().padStart(2, '0')}"
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF8EC), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Month and Year Header with Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous Month Button
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Month",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        currentDate = if (monthNumber == 1) {
                            LocalDate(year - 1, 12, 1)
                        } else {
                            LocalDate(year, monthNumber - 1, 1)
                        }
                    },
                tint = Color(0xFF1A1A1A)
            )

            // Month and Year Text
            Text(
                text = "$month $year",
                style = getPoppinsSemiBold14(),
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center
            )

            // Next Month Button
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Month",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        currentDate = if (monthNumber == 12) {
                            LocalDate(year + 1, 1, 1)
                        } else {
                            LocalDate(year, monthNumber + 1, 1)
                        }
                    },
                tint = Color(0xFF1A1A1A)
            )
        }

        // Day headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dayHeaders.forEach { dayHeader ->
                Text(
                    text = dayHeader,
                    style = getPoppinsMedium14(),
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Calendar grid
        val totalCells = daysInMonth + firstDayOfWeek
        val rows = (totalCells + 6) / 7

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (week in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (dayIndex in 0 until 7) {
                        val cellIndex = week * 7 + dayIndex
                        val dayNumber = cellIndex - firstDayOfWeek + 1

                        if (cellIndex < firstDayOfWeek || dayNumber > daysInMonth) {
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            val currentDayDate = LocalDate(year, monthNumber, dayNumber)
                            val isSunday = dayIndex == 6
                            val isPastDate = excludePastDates && currentDayDate < today
                            val isDisabled = (excludeSundays && isSunday) || isPastDate
                            val isSelected = selectedDate?.dayNumber == dayNumber.toString()

                            SingleDayCell(
                                day = dayNumber,
                                isSelected = isSelected,
                                isDisabled = isDisabled,
                                onClick = {
                                    if (!isDisabled) {
                                        // If clicking the same date, deselect it
                                        if (selectedDate?.dayNumber == dayNumber.toString()) {
                                            onDateSelected(null)
                                        } else {
                                            // Select the new date (replaces previous selection)
                                            onDateSelected(createDateModel(dayNumber))
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SingleDayCell(
    day: Int,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color(0xFF2196F3),
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            )
            .background(
                color = when {
                    isDisabled -> Color.Transparent
                    isSelected -> Color(0xFF2196F3)
                    else -> Color.White
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !isDisabled, onClick = onClick)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            style = if (isSelected) getPoppinsSemiBold14() else getPoppinsRegular14(),
            color = when {
                isDisabled -> Color(0xFFBDBDBD)
                isSelected -> Color.White
                else -> Color(0xFF1A1A1A)
            }
        )
    }
}


@Composable
fun RadioButton(text: String, checked: Boolean = false, isTermsClicked: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { isTermsClicked(!checked) }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { isTermsClicked(it) }
        )
        Text(
            text = text,
            style = getPoppinsMedium12(),
            color = Color(0xFF252525),
        )
    }
}


