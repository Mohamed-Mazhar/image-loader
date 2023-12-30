@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.photodisplayer.features.photos.presentation.screen

import android.util.Log
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.photodisplayer.common.theme.AccentColor
import com.example.photodisplayer.common.theme.PrimaryColor
import com.example.photodisplayer.common.theme.Purple40
import com.example.photodisplayer.common.theme.Purple80
import com.example.photodisplayer.common.ui.Screen
import com.example.photodisplayer.features.photos.presentation.viewmodel.PhotosScreenEvent
import com.example.photodisplayer.features.photos.presentation.viewmodel.PhotosScreenState


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoListScreen(
    navController: NavController,
    photosScreenState: PhotosScreenState,
    onAppEvent: (PhotosScreenEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = photosScreenState.isLoading,
        onRefresh = {
            onAppEvent(PhotosScreenEvent.RefreshPageEvent)
        }
    )
    LaunchedEffect(key1 = Unit) {
        onAppEvent(PhotosScreenEvent.ScreenLaunchedEvent)
    }
    Scaffold(
        topBar = {
            QuerySearch(
                query = photosScreenState.query,
                label = "Search",
                onQueryChanged = {
                    onAppEvent(PhotosScreenEvent.SearchFieldChangedEvent(it))
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
                    items(photosScreenState.filteredPhotos) {
                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Log.d("Marvel", "Navigating to next screen $it")
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
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxWidth(),
                                    onSuccess = {

                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = it.ellipsizedCaption,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = photosScreenState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = if (photosScreenState.isLoading) Purple40 else Purple80,
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
    onQueryChanged: (String) -> Unit
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