package com.aniper.app.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "설정",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // General section
        Text(
            text = "일반",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "배경화면 설정",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Text(
            text = "홈 화면 설정 메뉴에서 배경화면을 변경할 수 있습니다.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // About section
        Text(
            text = "앱 정보",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "버전 1.0.0",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "개발자: Aniper Team",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "배경화면 인터랙티브 캐릭터 앱 Aniper에 오신 것을 환영합니다!\n캐릭터를 만들고, 공유하고, 배경화면에서 즐겨보세요.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
