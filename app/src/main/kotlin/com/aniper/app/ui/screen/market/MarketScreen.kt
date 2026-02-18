package com.aniper.app.ui.screen.market

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.aniper.app.ui.screen.market.component.CharacterCard
import com.aniper.app.ui.screen.market.component.MarketDetailModal
import com.aniper.app.ui.screen.market.component.SearchBar
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun MarketScreen(
    navController: NavController,
    viewModel: MarketViewModel = hiltViewModel()
) {
    val characters by viewModel.marketCharacters.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val searchQuery by viewModel.searchQuery.collectAsState(initial = "")
    var selectedCharacter by remember { mutableStateOf<com.aniper.app.domain.model.MarketCharacter?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "마켓",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.search(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (isLoading) {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        } else if (characters.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "사용 가능한 캐릭터가 없습니다.",
                    color = TextSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(characters) { character ->
                        CharacterCard(
                            character = character,
                            onClick = { selectedCharacter = character },
                            onDownloadClick = {
                                viewModel.downloadCharacter(character)
                            },
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            )
        }
    }

    // Show detail modal if character is selected
    selectedCharacter?.let { character ->
        MarketDetailModal(
            character = character,
            onDismiss = { selectedCharacter = null },
            onDownloadClick = {
                viewModel.downloadCharacter(character)
                selectedCharacter = null
            }
        )
    }
}
