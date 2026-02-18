package com.aniper.app.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
import com.aniper.app.ui.component.common.LoadingIndicator
import com.aniper.app.ui.screen.home.component.CharacterDetailModal
import com.aniper.app.ui.screen.home.component.CharacterListItem
import com.aniper.app.ui.theme.BackgroundDark
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val characters by viewModel.characters.collectAsState(initial = emptyList())
    val activeCharacterId by viewModel.activeCharacterId.collectAsState(initial = null)
    var selectedCharacter by remember { mutableStateOf<com.aniper.app.domain.model.Character?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "내 캐릭터",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (characters.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "캐릭터가 없습니다.\n마켓에서 다운로드하거나 새로 만들어보세요.",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(32.dp)
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(characters) { character ->
                    CharacterListItem(
                        character = character,
                        isActive = character.id == activeCharacterId,
                        onSummonClick = {
                            viewModel.setActiveCharacter(character.id)
                        },
                        onDismissClick = {
                            viewModel.setActiveCharacter(null)
                        },
                        onRemoveClick = {
                            viewModel.deleteCharacter(character.id)
                        },
                        onDetailClick = {
                            selectedCharacter = character
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

    // Show detail modal if character is selected
    selectedCharacter?.let { character ->
        CharacterDetailModal(
            character = character,
            onDismiss = { selectedCharacter = null }
        )
    }
}
