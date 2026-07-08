package com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.avatar_line
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.camera_badge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exquisite.a_mobile_kmm.core.nav.UploadImage
import com.exquisite.a_mobile_kmm.core.camera.rememberCameraLauncher
import com.exquisite.a_mobile_kmm.core.theme.LocalColorsPalette
import com.exquisite.a_mobile_kmm.core.screen_components.AvatarIcon
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.a_mobile_kmm.core.screen_components.LineButtonBlackText
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.dripp.core.components.rememberSnackBar
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UploadImageScreen(
    uploadImage: UploadImage,
    goBack: () -> Unit,
    goToTheSuccessScreen: (String,String) -> Unit,
    modifier: Modifier = Modifier,
    uploadImageViewModel: UploadImageViewModel = koinViewModel<UploadImageViewModel>()
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()

    val completeProfileState = uploadImageViewModel.completeProfileState.collectAsStateWithLifecycle()
    val imageUploadState = uploadImageViewModel.imageUploadState.collectAsStateWithLifecycle()
    var imageUrl by remember {mutableStateOf("")}
    val scope = rememberCoroutineScope()
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var profilePictureByteArray by remember {mutableStateOf<ByteArray?>(null)}

    val cameraLauncher = rememberCameraLauncher { imageData ->
        imageData?.let {
            profilePictureByteArray = it
            uploadImageViewModel.uploadImage(it,"file")
        }
    }

    val imagePickerLaunch = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { imageData ->
                profilePictureByteArray = imageData
                uploadImageViewModel.uploadImage(imageData,"file")
            }
        }
    )

    when (val result = completeProfileState.value) {
        is CompleteProfileState.Success -> {
            goToTheSuccessScreen("Congratulations! Your account has\n been created successfully. Welcome to\n the community! \uD83E\uDD73","You're all set!")
        }
        is CompleteProfileState.Loading -> {
            LoadingDialog(true)
        }
        is CompleteProfileState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }
        is CompleteProfileState.Idle -> {
            LoadingDialog(false)
        }
    }

    when (val result = imageUploadState.value) {
        is ImageUploadState.Success -> {
            imageUrl = result.url

        }
        is ImageUploadState.Loading -> {
         //   LoadingDialog(true)
        }
        is ImageUploadState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }
        is ImageUploadState.Idle -> {
            LoadingDialog(false)
        }
    }

    // Request camera permission upfront for smoother UX
    LaunchedEffect(Unit) {
        cameraLauncher.requestPermission()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            color = Color.White
        )
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.align(Alignment.TopStart)
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.back_arrow), contentDescription = "back",
                modifier = modifier.clickable {
                    goBack()
                })
            Spacer(modifier = modifier.weight(1F))
            Text(
                text = "Skip", style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFF09103), textAlign = TextAlign.Center,
                modifier = modifier.clickable {
               uploadImageViewModel.completeProfile(uploadImage.password,uploadImage.uniqueRef,uploadImage.otp,imageUrl)
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = modifier.height(50.dp))

            Text(
                text = "Add a photo", style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF232323), textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "Add a profile picture so that\n we can recognise you",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF232323),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(60.dp))

                Box(modifier = Modifier.size(130.dp).clickable{
                    showImageSourceDialog = true
                }) {
                    if (profilePictureByteArray == null) {
                        AvatarIcon(130.dp, vectorResource(Res.drawable.avatar_line))
                        Image(
                            painter = painterResource(resource = Res.drawable.camera_badge),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (19).dp, y = (25).dp)
                        )
                    }else{
                        AsyncImage(
                            model = profilePictureByteArray,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .padding(top = 13.dp, start = 12.dp)
                                .size(130.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    LocalColorsPalette.current.borderColor,
                                    CircleShape
                                )
                        )

                        Image(
                            painter = painterResource(resource = Res.drawable.camera_badge),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (19).dp, y = (25).dp)
                                .clickable {
                                    showImageSourceDialog = true
                                }
                        )
                    }
                }


        }

        Column(
            modifier = modifier.align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            LineButtonBlackText(
                "Upload Image",
                {
                    uploadImageViewModel.completeProfile(uploadImage.password,uploadImage.uniqueRef,uploadImage.otp,imageUrl)
                })
        }

        // Image Source Selection Dialog
        if (showImageSourceDialog) {
            ImageSourceDialog(
                onDismiss = { showImageSourceDialog = false },
                onCameraSelected = {
                    showImageSourceDialog = false
                    cameraLauncher.launch()
                },
                onGallerySelected = {
                    showImageSourceDialog = false
                    imagePickerLaunch.launch()
                }
            )
        }

        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}


@Composable
fun ImageSourceDialog(
    onDismiss: () -> Unit,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Choose Image Source",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Select how you'd like to add your photo:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalColorsPalette.current.subTitleColor
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(text = "Take Photo", onClick = onCameraSelected)
                LineButtonBlackText(text = "Choose from Gallery", onClick = onGallerySelected)
            }
        },
        containerColor = Color(0xFF1A1A1A),
        modifier = modifier
    )
}
