package com.devtoolkit.pro.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devtoolkit.pro.ui.components.AdBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val themeMode by viewModel.themeMode.collectAsState()
    val dynamicColors by viewModel.dynamicColors.collectAsState()

    var showAboutDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            AdBanner(modifier = Modifier.navigationBarsPadding())
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
            // Appearance Header
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Theme Mode Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Theme Mode",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("system" to "System Default", "light" to "Light", "dark" to "Dark").forEach { (key, label) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { viewModel.setThemeMode(key) }
                            ) {
                                RadioButton(
                                    selected = themeMode == key,
                                    onClick = { viewModel.setThemeMode(key) }
                                )
                                Text(text = label, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }

            // Dynamic Color Toggle Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Dynamic Colors",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Match app colors to system wallpaper (Android 12+)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    Switch(
                        checked = dynamicColors,
                        onCheckedChange = { viewModel.setDynamicColors(it) }
                    )
                }
            }

            // General Header
            Text(
                text = "General",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Options List
            val options = listOf(
                SettingItemData("Rate App", Icons.Default.Star, {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.devtoolkit.pro"))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Rate functionality will trigger when published", Toast.LENGTH_SHORT).show()
                    }
                }),
                SettingItemData("Share App", Icons.Default.Share, {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Check out DevToolkit Pro: Everything a Developer Needs! Get it locally and work offline.")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share App"))
                }),
                SettingItemData("Privacy Policy", Icons.Default.PrivacyTip, { showPrivacyDialog = true }),
                SettingItemData("Terms of Service", Icons.Default.Description, { showTermsDialog = true }),
                SettingItemData("About DevToolkit Pro", Icons.Default.Info, { showAboutDialog = true })
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    options.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { item.onClick() }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                        if (index < options.lastIndex) {
                            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                        }
                    }
                }
            }
        }
    }

    // --- Dialogs ---
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("About DevToolkit Pro", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Version 1.0.0", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("DevToolkit Pro is a completely offline collection of essential tools designed for developers, architects, and designers. It operates 100% on device, securing your data locally with zero network logging.")
                }
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Policy", fontWeight = FontWeight.Bold) },
            text = {
                Box(modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())) {
                    Text("DevToolkit Pro respects your privacy. The application runs entirely offline. We do not gather, store, sync, or transmit any user data, JSON text, tokens, queries, or logs. Bookmarks, history, and notes are saved locally in the secure Jetpack Preferences DataStore on your device. The AdMob SDK serves advertising locally, complying with standard data collection profiles.")
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = { Text("Terms of Service", fontWeight = FontWeight.Bold) },
            text = {
                Box(modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())) {
                    Text("By installing and accessing DevToolkit Pro, you agree that this utility is provided 'as-is' without warranty of any kind. You are solely responsible for verifying the correctness of outputs, token decodes, formatting operations, and calculations. The offline tool operates on-device; no data is backed up to remote cloud environments, and uninstalling the application will delete your bookmarks, history, and notes permanently.")
                }
            },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

data class SettingItemData(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)
