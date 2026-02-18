package com.aniper.app.ui.screen.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aniper.app.domain.model.Motion
import com.aniper.app.ui.component.common.AnIperButton
import com.aniper.app.ui.screen.create.component.MotionUploadSlot
import com.aniper.app.ui.screen.create.component.TagInput
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun CreateCharacterScreen(
    navController: NavController,
    viewModel: CreateCharacterViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val tags by viewModel.tags.collectAsState()
    val motionImages by viewModel.motionImages.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val message by viewModel.message.collectAsState()
    var tagInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "캐릭터 생성",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Name input
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.setName(it) },
            label = { Text("캐릭터 이름") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = TextPrimary
            )
        )

        // Description input
        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.setDescription(it) },
            label = { Text("설명") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            maxLines = 3
        )

        // Tags
        Text(
            text = "태그 (3개)",
            style = MaterialTheme.typography.titleSmall,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TagInput(
            tags = tags,
            onAddTag = { viewModel.addTag(it) },
            onRemoveTag = { viewModel.removeTag(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Motion uploads
        Text(
            text = "모션 이미지 추가",
            style = MaterialTheme.typography.titleSmall,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            items(Motion.values()) { motion ->
                MotionUploadSlot(
                    motion = motion,
                    hasImage = motionImages.containsKey(motion),
                    onImageSelected = { path ->
                        viewModel.setMotionImage(motion, path)
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        // Message
        message?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AnIperButton(
                text = "취소",
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                enabled = !isSaving
            )

            AnIperButton(
                text = "저장하기",
                onClick = { viewModel.saveCharacter() },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                enabled = viewModel.canSave() && !isSaving
            )
        }
    }
}
