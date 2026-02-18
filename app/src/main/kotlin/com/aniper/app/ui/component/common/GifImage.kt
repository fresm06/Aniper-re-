package com.aniper.app.ui.component.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aniper.app.ui.theme.AccentPurple

@Composable
fun GifImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    isLoading: Boolean = false,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            contentScale = contentScale,
            onSuccess = { onSuccess() },
            onError = { onError() }
        )

        if (isLoading) {
            CircularProgressIndicator(
                color = AccentPurple,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
