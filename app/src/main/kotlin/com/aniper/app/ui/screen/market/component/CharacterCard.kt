package com.aniper.app.ui.screen.market.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.aniper.app.ui.theme.AccentPink
import com.aniper.app.ui.theme.BackgroundSecondary
import com.aniper.app.ui.theme.Success
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun CharacterCard(
    character: MarketCharacter,
    onClick: () -> Unit,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundSecondary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        // Character image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(140.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            GifImage(
                model = character.motions[Motion.IDLE],
                contentDescription = character.name,
                modifier = Modifier.matchParentSize()
            )

            // Approval checkmark if approved
            if (character.isApproved) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .background(Success, RoundedCornerShape(50))
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Approved",
                        tint = BackgroundSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Character info
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleSmall,
            color = TextPrimary,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = character.author,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )

        // Download count
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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

        // Download button
        AnIperButton(
            text = "다운로드",
            onClick = onDownloadClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}
