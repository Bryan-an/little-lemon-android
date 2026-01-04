package com.example.littlelemon.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(name = "Little Lemon - Light")
@Composable
private fun LittleLemonLightPreview() {
    LittleLemonTheme(darkTheme = false, dynamicColor = false) {
        PalettePreviewContent()
    }
}

@Preview(name = "Little Lemon - Dark")
@Composable
private fun LittleLemonDarkPreview() {
    LittleLemonTheme(darkTheme = true, dynamicColor = false) {
        PalettePreviewContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PalettePreviewContent() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Little Lemon") }) },
    ) { padding ->
        Column(
            modifier =
            Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Markazi Text (Display)", style = MaterialTheme.typography.displayMedium)
                    Text("Primary / Secondary / Tertiary", style = MaterialTheme.typography.titleMedium)
                    Text("Paragraph text sample", style = MaterialTheme.typography.bodyLarge)
                    Button(onClick = {}, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)) {
                        Text("Primary Button")
                    }
                    OutlinedButton(onClick = {}) {
                        Text("Outlined Button")
                    }
                }
            }
        }
    }
}
