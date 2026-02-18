package com.aniper.app.ui.screen.market.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aniper.app.domain.model.MarketCharacter
import com.aniper.app.domain.model.Motion
import com.aniper.app.ui.component.common.AnIperButton
import com.aniper.app.ui.component.common.GifImage
import com.aniper.app.ui.theme.BackgroundDark
import com.aniper.app.ui.theme.BackgroundSecondary
import com.aniper.app.ui.theme.Success
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun MarketDetailModal(
    character: MarketCharacter,
    onDismiss: () -> Unit,
    onDownloadClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark.copy(alpha = 0.7f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(BackgroundSecondary, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header with close button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary
                    )
                    if (character.isApproved) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Approved",
                            tint = Success,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(start = 8.dp)
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextSecondary
                    )
                }
            }

            // Author and download count
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "작성자: ${character.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.GetApp,
                    contentDescription = "Downloads",
                    tint = TextSecondary,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${character.downloadCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Character description
            Text(
                text = character.description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Tags
            if (character.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    character.tags.forEach { tag ->
                        AssistChip(
                            onClick = {},
                            label = { Text(text = tag) },
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }

            // Motion grid (2x4)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(Motion.values()) { motion ->
                        MotionGridItem(
                            motion = motion,
                            imageUrl = character.motions[motion],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                    }
                }
            )

            // Download button
            AnIperButton(
                text = "다운로드",
                onClick = onDownloadClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun MotionGridItem(
    motion: Motion,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            GifImage(
                model = imageUrl,
                contentDescription = motion.displayName,
                modifier = Modifier.matchParentSize()
            )
        }
        Text(
            text = motion.displayName,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
