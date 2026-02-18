package com.aniper.app.ui.screen.create.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aniper.app.ui.theme.TextPrimary

@Composable
fun TagInput(
    tags: List<String>,
    onAddTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var tagText by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        // Current tags as chips
        if (tags.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach { tag ->
                    InputChip(
                        selected = false,
                        onClick = {},
                        label = { Text(text = tag) },
                        trailingIcon = {
                            IconButton(
                                onClick = { onRemoveTag(tag) },
                                modifier = Modifier.padding(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove"
                                )
                            }
                        }
                    )
                }
            }
        }

        // Input field for new tags
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = tagText,
                onValueChange = { tagText = it.take(15) },
                label = { Text("새 태그") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                maxLines = 1,
                enabled = tags.size < 3
            )

            IconButton(
                onClick = {
                    if (tagText.isNotBlank() && tags.size < 3) {
                        onAddTag(tagText)
                        tagText = ""
                    }
                },
                enabled = tagText.isNotBlank() && tags.size < 3
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add tag"
                )
            }
        }

        if (tags.size < 3) {
            Text(
                text = "${tags.size}/3 태그 추가됨",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
