package com.example.photodisplayer.features.photodetails.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photodisplayer.R
import com.example.photodisplayer.common.util.getContrastColor
import com.example.photodisplayer.common.util.getDominantColor
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsEvent
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsViewModel


@Composable
fun PhotoDetailsScreen(
    navController: NavController,
    id: String,
    photoDetailsViewModel: PhotoDetailsViewModel = viewModel(factory = PhotoDetailsViewModel.Factory),
) {
    var backgroundColor by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        photoDetailsViewModel.onEvent(PhotoDetailsEvent.ScreenLaunched(id))
    }
    if (photoDetailsViewModel.state.updateCompleted) {
        navController.navigateUp()
    }
    if (photoDetailsViewModel.state.isLoading) {
        ShowLoading()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(backgroundColor))
    ) {
        AsyncImage(
            modifier = Modifier
                .width(photoDetailsViewModel.state.image?.width?.dp ?: 0.dp)
                .height(photoDetailsViewModel.state.image?.height?.dp ?: 0.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoDetailsViewModel.state.image?.imagePath)
                .allowHardware(false)
                .build(),
            contentDescription = null,
            placeholder = rememberVectorPainter(image = Icons.Default.Person),
            onSuccess = {
                val bitmap = it.result.drawable.toBitmap()
                val color = getDominantColor(bitmap)
                backgroundColor = color
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = photoDetailsViewModel.state.image?.caption ?: "",
            onValueChange = {
                photoDetailsViewModel.onEvent(PhotoDetailsEvent.CaptionChanged(it))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.no_caption),
                    style = TextStyle(
                        color = Color(getContrastColor(backgroundColor))
                    )
                )
            },
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 8.dp
                )
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(getContrastColor(backgroundColor)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (photoDetailsViewModel.state.image?.caption.isNullOrBlank()) Color.Red else Color.Black,
                unfocusedBorderColor = if (photoDetailsViewModel.state.image?.caption.isNullOrBlank()) Color.Red else Color.Black
            )

        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                photoDetailsViewModel.onEvent(PhotoDetailsEvent.UpdatePhotoDetails)
            }) {
                Text(text = stringResource(id = R.string.update))
            }

            Button(onClick = {
                photoDetailsViewModel.onEvent(PhotoDetailsEvent.CompressPhoto)
            }) {
                Text(text = stringResource(id = R.string.compress))
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        ImageHeightAndWidth(
            width = photoDetailsViewModel.state.image?.width,
            height = photoDetailsViewModel.state.image?.height,
            onWidthChanged = {
                photoDetailsViewModel.onEvent(PhotoDetailsEvent.UpdatePhotoWidth(it))
            },
            onHeightChanged = {
                photoDetailsViewModel.onEvent(PhotoDetailsEvent.UpdatePhotoHeight(it))
            },
            textColor = backgroundColor
        )
    }
}

@Composable
fun ShowLoading() {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ImageHeightAndWidth(
    width: Int?,
    height: Int?,
    textColor: Int,
    onWidthChanged: (width: String) -> Unit,
    onHeightChanged: (height: String) -> Unit,
) {
    val contrastColor by remember {
        mutableStateOf(Color(getContrastColor(textColor)))
    }
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = if (height != 0) height.toString() else "",
            onValueChange = onHeightChanged,
            textStyle = TextStyle(
                color = contrastColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            label = {
                Text(text = "Height")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = contrastColor,
                unfocusedBorderColor = contrastColor,
                focusedLabelColor = contrastColor,
                unfocusedLabelColor = contrastColor,
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = if (width != 0) width.toString() else "",
            onValueChange = onWidthChanged,
            textStyle = TextStyle(
                color = contrastColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            label = {
                Text(text = "Width")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = contrastColor,
                unfocusedBorderColor = contrastColor,
                focusedLabelColor = contrastColor,
                unfocusedLabelColor = contrastColor,
            )
        )
    }
}