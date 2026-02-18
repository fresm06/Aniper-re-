package com.aniper.app.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aniper.app.domain.model.Character
import com.aniper.app.domain.model.Motion
import com.aniper.app.ui.component.common.AnIperButton
import com.aniper.app.ui.component.common.GifImage
import com.aniper.app.ui.theme.BackgroundSecondary
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun CharacterListItem(
    character: Character,
    isActive: Boolean = false,
    onSummonClick: () -> Unit,
    onDismissClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundSecondary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onDetailClick() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Character thumbnail
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            GifImage(
                model = character.motions[Motion.IDLE],
                contentDescription = character.name,
                modifier = Modifier.matchParentSize()
            )
        }

        // Character info
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = character.author,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        // Action buttons
        Column(
            modifier = Modifier.align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when {
                isActive -> {
                    AnIperButton(
                        text = "소환 해제",
                        onClick = onDismissClick,
                        modifier = Modifier.height(36.dp)
                    )
                }
                else -> {
                    AnIperButton(
                        text = "소환",
                        onClick = onSummonClick,
                        modifier = Modifier.height(36.dp)
                    )
                    AnIperButton(
                        text = "제거",
                        onClick = onRemoveClick,
                        modifier = Modifier.height(36.dp),
                        containerColor = com.aniper.app.ui.theme.BackgroundSecondary
                    )
                }
            }
        }
    }
}
