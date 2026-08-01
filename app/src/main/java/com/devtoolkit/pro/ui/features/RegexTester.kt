package com.devtoolkit.pro.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegexTesterScreen(onBackClick: () -> Unit) {
    var regexInput by remember { mutableStateOf("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)") }
    var sampleText by remember { mutableStateOf("Contact us at support@devtoolkit.com or admin@protools.org for help.") }
    
    var errorMsg by remember { mutableStateOf("") }
    var matchCount by remember { mutableIntStateOf(0) }
    var matchesList by remember { mutableStateOf<List<String>>(emptyList()) }

    val secondaryColor = MaterialTheme.colorScheme.secondary

    // Highlights matches
    val highlightedText = remember(regexInput, sampleText, secondaryColor) {
        buildAnnotatedString {
            append(sampleText)
            if (regexInput.isBlank()) {
                errorMsg = ""
                matchCount = 0
                matchesList = emptyList()
                return@buildAnnotatedString
            }
            try {
                val regex = Regex(regexInput)
                val matches = regex.findAll(sampleText).toList()
                errorMsg = ""
                matchCount = matches.size
                matchesList = matches.map { it.value }

                matches.forEach { match ->
                    addStyle(
                        style = SpanStyle(
                            background = secondaryColor.copy(alpha = 0.3f),
                            color = secondaryColor,
                            fontWeight = FontWeight.Bold
                        ),
                        start = match.range.first,
                        end = match.range.last + 1
                    )
                }
            } catch (e: Exception) {
                errorMsg = "Invalid Regex: ${e.localizedMessage}"
                matchCount = 0
                matchesList = emptyList()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Regex Tester", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = regexInput,
                onValueChange = { regexInput = it },
                label = { Text("Regular Expression Pattern") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = sampleText,
                onValueChange = { sampleText = it },
                label = { Text("Sample Test Text") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp)
            )

            if (errorMsg.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = errorMsg, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Matches Found:", fontWeight = FontWeight.Bold)
                        Badge(containerColor = MaterialTheme.colorScheme.primary) {
                            Text("$matchCount", modifier = Modifier.padding(horizontal = 6.dp), color = Color.White)
                        }
                    }
                }
            }

            // Highlight Output Window
            Text(
                text = "Highlight Viewer",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = highlightedText,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (matchesList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Matched Items",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        matchesList.forEachIndexed { index, match ->
                            Text(
                                text = "${index + 1}:  $match",
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (index < matchesList.lastIndex) {
                                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                            }
                        }
                    }
                }
            }
        }
    }
}
