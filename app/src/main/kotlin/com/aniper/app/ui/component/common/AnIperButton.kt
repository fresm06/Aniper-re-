package com.aniper.app.ui.component.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aniper.app.ui.theme.AccentPurple
import com.aniper.app.ui.theme.AccentPurpleLight
import com.aniper.app.ui.theme.BackgroundDark
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

@Composable
fun AnIperButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = AccentPurple,
    contentColor: Color = BackgroundDark,
    disabledContainerColor: Color = AccentPurple.copy(alpha = 0.5f),
    disabledContentColor: Color = TextPrimary.copy(alpha = 0.5f)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun AnIperOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderColor: Color = AccentPurple
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.OutlinedButtonDefaults.outlinedButtonColors(
            contentColor = AccentPurple,
            disabledContentColor = TextPrimary.copy(alpha = 0.5f)
        ),
        border = androidx.compose.material3.BorderStroke(1.dp, borderColor)
    ) {
        Text(text = text)
    }
}
