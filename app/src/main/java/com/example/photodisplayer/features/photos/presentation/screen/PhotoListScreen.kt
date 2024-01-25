@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.photodisplayer.features.photos.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.photodisplayer.R
import com.example.photodisplayer.common.theme.AccentColor
import com.example.photodisplayer.common.theme.PrimaryColor
import com.example.photodisplayer.common.theme.Purple40
import com.example.photodisplayer.common.theme.Purple80
import com.example.photodisplayer.common.ui.Screen
import com.example.photodisplayer.features.photos.presentation.viewmodel.PhotosScreenEvent
import com.example.photodisplayer.features.photos.presentation.viewmodel.PhotosViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoListScreen(
    navController: NavController,
    photosViewModel: PhotosViewModel = viewModel(factory = PhotosViewModel.Factory),
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = photosViewModel.state.isLoading,
        onRefresh = {
            photosViewModel.onEvent(PhotosScreenEvent.RefreshPageEvent)
        }
    )
    LaunchedEffect(key1 = Unit) {
        photosViewModel.onEvent(PhotosScreenEvent.ScreenLaunchedEvent)
    }
    if (photosViewModel.state.errorMessage.isNotBlank()) {
        ShowAlertDialog(
            message = photosViewModel.state.errorMessage,
            onDismiss = {
                photosViewModel.onEvent(PhotosScreenEvent.DismissErrorDialog)
            }
        )
    }
    Scaffold(
        topBar = {
            QuerySearch(
                query = photosViewModel.state.query,
                label = stringResource(id = R.string.search),
                onQueryChanged = {
                    photosViewModel.onEvent(PhotosScreenEvent.SearchFieldChangedEvent(it))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(photosViewModel.state.filteredPhotos) {
                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.PhotoDetailsScreen.route.replace("{id}", it.id)
                                    )
                                }
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier.padding(
                                        start = 4.dp,
                                        top = 4.dp
                                    ),
                                    text = it.name,
                                    style = TextStyle(
                                        color = PrimaryColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                AsyncImage(
                                    model = it.imagePath,
                                    contentDescription = null,
                                    placeholder = rememberVectorPainter(image = Icons.Default.Person),
                                    onSuccess = { imageState ->
                                        val imageDetails = imageState.result
                                        photosViewModel.onEvent(
                                            event = PhotosScreenEvent.UpdatePhotoDetails(
                                                photoId = it.id,
                                                height = imageDetails.drawable.intrinsicHeight,
                                                width = imageDetails.drawable.intrinsicWidth
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = it.ellipsizedCaption.ifEmpty {
                                        stringResource(id = R.string.no_caption)
                                    },
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = photosViewModel.state.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = if (photosViewModel.state.isLoading) Purple40 else Purple80,
                )
            }
        }
    )
}


@Composable
fun QuerySearch(
    modifier: Modifier = Modifier,
    query: String,
    label: String,
    onQueryChanged: (String) -> Unit,
) {

    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = AccentColor,
            focusedIndicatorColor = AccentColor,
        )

    )
}

@Composable
fun ShowAlertDialog(
    message: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text("Ok")
            }
        }
    )
}