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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
        modifier = Modifier.fillMaxSize().background(Color(backgroundColor))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoDetailsViewModel.state.marvelCharacter?.imagePath)
                .allowHardware(false)
                .build(),
            contentDescription = null,
            placeholder = rememberVectorPainter(image = Icons.Default.Person),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            onSuccess = {
                val bitmap = it.result.drawable.toBitmap()
                val color = getDominantColor(bitmap)
                backgroundColor = color
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = photoDetailsViewModel.state.marvelCharacter?.caption ?: "",
            onValueChange = {
                photoDetailsViewModel.onEvent(PhotoDetailsEvent.CaptionChanged(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.no_caption))
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
                focusedBorderColor = if (photoDetailsViewModel.state.marvelCharacter?.caption.isNullOrBlank()) Color.Red else Color.Black,
                unfocusedBorderColor = if (photoDetailsViewModel.state.marvelCharacter?.caption.isNullOrBlank()) Color.Red else Color.Black
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