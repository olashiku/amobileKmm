package com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_document

import amobilekmm.shared.generated.resources.Res
import amobilekmm.shared.generated.resources.back_arrow
import amobilekmm.shared.generated.resources.upload_image_icon
import amobilekmm.shared.generated.resources.upload_picture_icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exquisite.a_mobile_kmm.core.camera.rememberCameraLauncher
import com.exquisite.a_mobile_kmm.core.file_picker.rememberFilePicker
import com.exquisite.a_mobile_kmm.core.screen_components.FixedHeaderWithBackButton
import com.exquisite.a_mobile_kmm.core.screen_components.MediaSourceDialog
import com.exquisite.a_mobile_kmm.core.screen_components.PrimaryButton
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsMedium14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular12
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular14
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsRegular16
import com.exquisite.a_mobile_kmm.core.theme.getPoppinsSemiBold18
import com.exquisite.a_mobile_kmm.feature.auth.presenter.upload_image.ImageUploadState
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.DocumentType
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model.RegisterCleanerRequest
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration.CleanersRegistrationState
import com.exquisite.a_mobile_kmm.feature.cleaners_registration.presenter.cleaners_registration.CleanersRegistrationViewModel
import com.exquisite.dripp.core.components.CustomSnackbarHost
import com.exquisite.dripp.core.components.LoadingDialog
import com.exquisite.dripp.core.components.rememberSnackBar
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CleanersDocumentUploadScreen(
    request:RegisterCleanerRequest,
    goBack: () -> Unit,
    goToSuccessPage: (String, String,String) -> Unit,
    viewModel: CleanersRegistrationViewModel = koinViewModel<CleanersRegistrationViewModel>(),
    modifier: Modifier = Modifier
) {
    val (snackBar, snackBarHostState) = rememberSnackBar()


    val imageUploadState = viewModel.imageUploadState.collectAsStateWithLifecycle()
  val cleanersRegistrationState = viewModel.cleanersRegistrationState.collectAsStateWithLifecycle()
    var selectedDocument by remember { mutableStateOf<DocumentType?>(null) }

    var resumeUrl by remember {mutableStateOf("")}
    var profilePictureUrl by remember {mutableStateOf("")}

    val scope = rememberCoroutineScope()
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var imageByte by remember {mutableStateOf<ByteArray?>(null)}


    val cameraLauncher = rememberCameraLauncher { imageData ->
        imageData?.let {
            imageByte = it
            viewModel.uploadImage(it,"file")
        }
    }

    val imagePickerLaunch = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { imageData ->
                imageByte = imageData
                viewModel.uploadImage(imageData,"file")
            }
        }
    )

    val filePickerLauncher = rememberFilePicker(
        allowedExtensions = listOf("pdf", "doc", "docx"),
        onResult = { fileResult ->
            fileResult?.let {
                imageByte = it.data
                viewModel.uploadImage(it.data, "file")
            }
        }
    )

    // Request camera permission upfront for smoother UX
    LaunchedEffect(Unit) {
        cameraLauncher.requestPermission()
    }

    when (val result = imageUploadState.value) {
        is ImageUploadState.Success -> {
            when(selectedDocument) {
                DocumentType.Resume -> {
                    resumeUrl = result.url
                    selectedDocument = null
                    viewModel.clearImageState()
                }
                DocumentType.Picture -> {
                    profilePictureUrl = result.url
                    selectedDocument = null
                    viewModel.clearImageState()
                }
                null -> {}
            }
        }
        is ImageUploadState.Loading -> {
              LoadingDialog(true)
        }
        is ImageUploadState.Error -> {
            snackBar.showError("Error: ${result.message}")
        }
        is ImageUploadState.Idle -> {
            LoadingDialog(false)
        }
    }

     when(val result = cleanersRegistrationState.value){
       is CleanersRegistrationState.Idle ->{}
         is CleanersRegistrationState.Loading ->{
             LoadingDialog(true)
         }
         is CleanersRegistrationState.Success ->{
             LaunchedEffect(result){
                 viewModel.clearState()
                 viewModel.clearFormData()
                 goToSuccessPage.invoke("Thank you for registering","A customer service representative will get in touch with you shortly","Back to Home")

             }

         }
         is CleanersRegistrationState.Error ->{
             snackBar.showError("Error: ${result.message}")
         }

     }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            // Fixed Header
            FixedHeaderWithBackButton(
                title = "Registration Form",
                onBackClick = goBack
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
            ) {
                Spacer(modifier = modifier.height(22.dp))
                Text(text = "Please provide the following documents to\n" +
                        "complete your application.",
                    style = getPoppinsRegular14(), color = Color(0xFF252525))
                Spacer(modifier = modifier.height(32.dp))

               Text(text = "Resume Upload", style = getPoppinsRegular16(), color = Color(0xFF252525))
                Spacer(modifier = modifier.height(12.dp))
                Column(
                    modifier = modifier
                        .clickable {
                            selectedDocument = DocumentType.Resume
                            showImageSourceDialog = true
                        }
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(if(resumeUrl.isEmpty())0xFFFFF8EE else 0xFFE3FFE5))
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val dashWidth = 10f
                            val dashGap = 10f
                            drawRoundRect(
                                color = Color(0xFFD1D1D1),
                                style = Stroke(
                                    width = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(dashWidth, dashGap),
                                        phase = 0f
                                    )
                                ),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx())
                            )
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = modifier.height(30.dp))

                    Image(
                        painter = painterResource(Res.drawable.upload_picture_icon),
                        contentDescription = "Upload Picture"
                    )
                    Spacer(modifier = modifier.height(12.dp))
                    Text(text = "Click to upload Resume", style = getPoppinsMedium14(), color = Color(0xFF1F1F1F))
                    Spacer(modifier = modifier.height(4.dp))
                    Text(text = "PDF or DOCX (Max 5MB)", style = getPoppinsRegular12(), color = Color(0xFF757575))
                    Spacer(modifier = modifier.height(30.dp))
                }

                Spacer(modifier = modifier.height(24.dp))
                Text(text = "Picture Upload", style = getPoppinsRegular16(), color = Color(0xFF252525))
                Spacer(modifier = modifier.height(12.dp))

                Column(
                    modifier = modifier
                        .clickable {
                            selectedDocument = DocumentType.Picture
                            showImageSourceDialog = true
                        }
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color( if(profilePictureUrl.isEmpty())0xFFFFF8EE else 0xFFE3FFE5))
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val dashWidth = 10f
                            val dashGap = 10f
                            drawRoundRect(
                                color = Color(0xFFD1D1D1),
                                style = Stroke(
                                    width = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(dashWidth, dashGap),
                                        phase = 0f
                                    )
                                ),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx())
                            )
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = modifier.height(30.dp))

                    Image(
                        painter = painterResource(Res.drawable.upload_image_icon),
                        contentDescription = "Upload Picture"
                    )
                    Spacer(modifier = modifier.height(12.dp))
                    Text(text = "Click to upload Picture", style = getPoppinsMedium14(), color = Color(0xFF1F1F1F))
                    Spacer(modifier = modifier.height(4.dp))
                    Text(text = "JPG or PNG (Max 5MB)", style = getPoppinsRegular12(), color = Color(0xFF757575))
                    Spacer(modifier = modifier.height(30.dp))
                }
            }
        }

        PrimaryButton("Apply", {
            if(resumeUrl.isNotEmpty() && profilePictureUrl.isNotEmpty()){
                viewModel.registerCleaner(request.fullName,request.email,request.phone,request.address,resumeUrl, listOf(profilePictureUrl),request.gender,request.employmentStatus, request.experienceLevel)
            }

        }, modifier =  Modifier.align(BottomCenter).padding(20.dp))


        // Media Source Selection Dialog
        if (showImageSourceDialog) {
            when (selectedDocument) {
                DocumentType.Resume -> {
                    // For resume, show document picker and camera/gallery options
                    MediaSourceDialog(
                        onDismiss = { showImageSourceDialog = false },
                        title = "Upload Resume",
                        description = "Choose how you'd like to upload your resume:",
                        showCamera = true,
                        showGallery = true,
                        showDocument = true,
                        cameraButtonText = "Take Photo",
                        galleryButtonText = "Choose from Gallery",
                        documentButtonText = "Choose Document (PDF/DOCX)",
                        onCameraSelected = {
                            showImageSourceDialog = false
                            cameraLauncher.launch()
                        },
                        onGallerySelected = {
                            showImageSourceDialog = false
                            imagePickerLaunch.launch()
                        },
                        onDocumentSelected = {
                            showImageSourceDialog = false
                            filePickerLauncher.launch()
                        }
                    )
                }
                DocumentType.Picture -> {
                    // For picture, only show camera and gallery options
                    MediaSourceDialog(
                        onDismiss = { showImageSourceDialog = false },
                        title = "Upload Picture",
                        description = "Choose how you'd like to upload your picture:",
                        showCamera = true,
                        showGallery = true,
                        showDocument = false,
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
                null -> {
                    // Fallback
                    MediaSourceDialog(
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
            }
        }


        // Snackbar at bottom
        CustomSnackbarHost(
            snackbarHostState = snackBarHostState,
            modifier = Modifier.align(BottomCenter).padding(20.dp)
        )
    }


}