package com.aniper.app.ui.screen.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aniper.app.domain.model.Motion
import com.aniper.app.ui.theme.AccentPurple
import com.aniper.app.ui.theme.BackgroundSecondary
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun MotionUploadSlot(
    motion: Motion,
    hasImage: Boolean = false,
    onImageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(BackgroundSecondary, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .border(
                    2.dp,
                    if (hasImage) AccentPurple else AccentPurple.copy(alpha = 0.5f),
                    RoundedCornerShape(8.dp)
                )
                .clickable {
                    // In a real app, this would open a file picker
                    onImageSelected("path/to/${motion.name}.png")
                },
            contentAlignment = Alignment.Center
        ) {
            if (hasImage) {
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AccentPurple
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = "Upload",
                    tint = TextSecondary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Text(
            text = motion.displayName,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            modifier = androidx.compose.foundation.layout.Modifier.padding(top = 4.dp)
        )
    }
}
