package com.example.photodisplayer.features.photodetails.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.photodisplayer.common.theme.PrimaryColor
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsEvent
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsState


@Composable
fun PhotoDetailsScreen(
    navController: NavController,
    id: String,
    screenState: PhotoDetailsState,
    onAppEvent: (PhotoDetailsEvent) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onAppEvent(PhotoDetailsEvent.ScreenLaunched(id))
    }
    if (screenState.updateCompleted) {
        LaunchedEffect(key1 = Unit) {
            onAppEvent(PhotoDetailsEvent.ResetScreenState)
            navController.popBackStack()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = screenState.marvelCharacter?.imagePath,
            contentDescription = null,
            placeholder = rememberVectorPainter(image = Icons.Default.Person),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = screenState.marvelCharacter?.caption ?: "",
            onValueChange = {
                onAppEvent(PhotoDetailsEvent.CaptionChanged(it))
            },
            placeholder = {
                Text(text = "No Caption")
            },
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 8.dp
                )
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = PrimaryColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (screenState.marvelCharacter?.caption.isNullOrBlank()) Color.Red else Color.Black,
                unfocusedBorderColor = if (screenState.marvelCharacter?.caption.isNullOrBlank()) Color.Red else Color.Black
            )

        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onAppEvent(PhotoDetailsEvent.UpdatePhotoDetails)
        }) {
            Text(text = "Update")
        }
    }
}